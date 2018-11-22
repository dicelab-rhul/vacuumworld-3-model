#!/bin/bash

PKG_NAME="python3"
PKG="python3"
PKG_OK=$(which "$PKG")

echo Checking for "$PKG"... $PKG_OK

if [ "" == "$PKG_OK" ]; then
    echo "$PKG could not be found. Install $PKG_NAME and retry. Aborting..."
    exit 1
else
    echo "$PKG found! It is provided by $PKG_NAME"
    echo ""
fi

DEFAULT_PYTHON3=$(/usr/bin/env python3 --version |& cut -d' ' -f2)
MAJOR=$(echo ${DEFAULT_PYTHON3} | cut -d'.' -f1)
MINOR=$(echo ${DEFAULT_PYTHON3} | cut -d'.' -f2)

if [[ ${MAJOR} < "3" ]]; then
    echo "Python 3.6+ is required. Aborting..."

    exit 1
elif [ ${MAJOR} == "3" ]; then
    if [[ ${MINOR} < "6" ]]; then
        echo "Python 3.6+ is required. Aborting..."

        exit 1
    else
        echo "The ${PKG} sub-version looks good."
        echo ""
    fi
else  
    echo "The ${PKG} sub-version looks good."
    echo ""
fi

/usr/bin/env python3 - <<END

import sys
import os
import shutil


from subprocess import Popen, call, check_output, DEVNULL


def __check_for_mandatory_software() -> None:
    mandatory_software = {"git": "git", "ssh": "ssh", "mvn": "maven", "java": "openjdk-10-jdk", "javac": "openjdk-10-jdk", "curl": "curl", "grep": "grep"}

    print("##############################")
    print("# Mandatory Software Section #")
    print("##############################\n")

    for sw, pkg in mandatory_software.items():
        print("Checking for %s ..." % sw)

        output: str = check_output(["which", sw])

        if output == "":
            __die(message="Could not find %s. Please install %s and retry." % (sw, pkg))
        else:
            print("Found %s. Continuing...\n" % sw)


def __check_for_java_version() -> str:
    try:
        tokens: list = str(check_output(["java", "--version"]), "utf-8").split("\n")
        first_line_tokens: list = tokens[0].split(" ")
        provider: str = first_line_tokens[0]

        if "openjdk" == provider:
            version: str = first_line_tokens[1]
            __check_java_version(candidate=version, provider=provider)
            print("Java version: %s. Good!" % version)

            return version
        elif "java" == provider:
            version: str = first_line_tokens[2].replace("\"", "")
            __check_java_version(candidate=version, provider=provider)
            print("Java version: %s. Good!" % version)

            return version
        else:
            __die("There is something wrong with Java. Please double check that a provider for Java 10 is installed, and set as default Java.")

            return ""
    except Exception:
        __die("There is something wrong with Java. Please double check that a provider for Java 10 is installed, and set as default Java.")

        return ""


def __check_for_javac_version(java_version: str) -> None:
    try:
        tokens: list = str(check_output(["javac", "--version"]), "utf-8").split(" ")
        version: str = tokens[1].replace("\n", "").replace("\r", "")

        if version != java_version:
            __die("Java version: %s. Javac version: %s. Please fix this mismatching and retry." % (java_version, version))
        else:
            print("Javac version: %s. Good!\n" % version)
    except Exception:
        __die("There is something wrong with Javac. Please double check that a provider for Java 10 is installed, and set as default Javac.")


def __check_java_version(candidate: str, provider: str) -> None:
    tokens: list = [int(_, 10) for _ in candidate.split(".")]

    if "openjdk" == provider and tokens[0] < 10:
        __die("Your Java version appears to be %d. %d is the minimum needed." % (tokens[0], 10))
    elif "java" == provider and (tokens[0] < 1 or tokens[0] == 1 and tokens[1] < 10):
        __die("Your Java version appears to be %d.%d. %d.%d is the minimum needed." % (tokens[0], tokens[1], 1, 10))
    elif "openjdk" == provider or "java" == provider:
        return
    else:
        __die("There is something wrong with Java. Please double check that a provider for Java 10 is installed, and set as default Java.")


def __setup_ssh(bitbucket_public_project_url: str) -> None:
    print("#####################")
    print("# SSH Setup Section #")
    print("#####################\n")

    my_home: str = os.path.expanduser("~")
    ssh_dir: str = os.path.join(my_home, ".ssh")
    ssh_config_file_path: str = os.path.join(ssh_dir, "config")
    ssh_private_key_name: str = "id_ecdsa_vw3_readonly"
    ssh_public_key_name: str = "id_ecdsa_vw3_readonly.pub"
    ssh_private_key_path: str = os.path.join(ssh_dir, ssh_private_key_name)
    ssh_public_key_path: str = os.path.join(ssh_dir, ssh_public_key_name)

    __create_dir_if_necessary(path=ssh_dir)
    __download_key_if_necessary(key_url=bitbucket_public_project_url + "/" + ssh_private_key_name, path=ssh_private_key_path, mode=0o600)
    __download_key_if_necessary(key_url=bitbucket_public_project_url + "/" + ssh_public_key_name, path=ssh_public_key_path, mode=0o644)
    __create_or_update_config_file_if_necessary(ssh_private_key_path=ssh_private_key_path, ssh_config_file_path=ssh_config_file_path)


def __download_key_if_necessary(key_url: str, path: str, mode=int) -> None:
    if not os.path.exists(path=path):
        print("Downloading %s to %s ..." % (key_url, path))
        call(["curl", "-L", key_url, "-o", path])

        if not os.path.isfile(path=path):
            __die("Failed to download %s to %s." % (key_url, path))

            return
        else:
            print("Done.\n")
    elif not os.path.isfile(path=path):
        __die("%s already exists, but it is not a file." % path)

        return

    os.chmod(path=path, mode=mode)


def __create_dir_if_necessary(path: str, mode: int=0o744) -> None:
    if not os.path.exists(path=path):
        os.makedirs(name=path, mode=0o744)
    elif not os.path.isdir(s=path):
        __die("%s already exists, but it is not a directory." % path)


def __create_or_update_config_file_if_necessary(ssh_private_key_path: str, ssh_config_file_path: str) -> None:
    if not os.path.exists(path=ssh_config_file_path):
        with open(file=ssh_config_file_path, mode="w") as f:
            f.write("Host vacuumworld3bitbucket\nhostname bitbucket.org\nport 22\nuser git\nidentityfile %s\n" % ssh_private_key_path)
            f.flush()
    elif os.path.isfile(path=ssh_config_file_path):
        if not __check_entry_in_ssh_config_file(file_path=ssh_config_file_path, entry="Host vacuumworld3bitbucket"):
            with open(file=ssh_config_file_path, mode="a") as f:
                f.write("\nHost vacuumworld3bitbucket\nhostname bitbucket.org\nport 22\nuser git\nidentityfile %s\n" % ssh_private_key_path)
                f.flush()
        else:
            print("No need to modify %s.\n" % ssh_config_file_path)
    else:
        __die("%s already exists, but is is not a regular file." % ssh_config_file_path)


def __check_entry_in_ssh_config_file(file_path: str, entry: str) -> bool:
    with open(file=file_path, mode="r") as f:
        for line in f.readlines():
            if "Host vacuumworld3bitbucket" in line:
                return True

        return False


def __setup_maven_dependencies(working_dir: str, branch: str="master") -> None:
    print("##############################")
    print("# Maven Dependencies Section #")
    print("##############################\n")

    maven_repo_dir: str = os.path.join(os.path.expanduser("~"), ".m2", "repository")

    maven_dependencies: list[dict[str: str]] = [
        {"gid": "org.cloudstrife9999", "aid": "LogUtilities", "name": "LogUtilities"},
        {"gid": "uk.ac.rhul.cs.dice", "aid": "agentcommon", "name": "agentcommon"},
        {"gid": "uk.ac.rhul.cs.dice", "aid": "agentcontainers", "name": "agentcontainers"},
        {"gid": "uk.ac.rhul.cs.dice", "aid": "agentprototype", "name": "agentprototype"},
        {"gid": "uk.ac.rhul.cs.dice", "aid": "agentactions", "name": "agentactions"},
        {"gid": "uk.ac.rhul.cs.dice.vacuumworld", "aid": "vwcommon", "name": "vwcommon"}
    ]

    version: str = "1.0.1"

    workspace: str = os.path.join(working_dir, "workspace")
    __create_dir_if_necessary(path=workspace)

    for maven_dependency in maven_dependencies:
        new_version: bool = __clone_or_pull_project(maven_data=maven_dependency, workspace=workspace, branch=branch)
        __build_and_install_dependency_if_necessary(maven_data=maven_dependency, workspace=workspace, maven_repo_dir=maven_repo_dir, new_version=new_version, version=version)


def __clone_or_pull_project(maven_data: dict, workspace: str, branch: str) -> bool:
    name: str = maven_data["name"]
    bitbucket_project_url: str = "git@vacuumworld3bitbucket:rhul-dice-privacy/%s.git" % name
    project_dir: str = os.path.join(workspace, name)

    if os.path.isdir(s=project_dir):
        old_dir: str = os.getcwd()
        os.chdir(path=project_dir)

        print("Working with %s ..." % project_dir)

        Popen(["git", "checkout", branch], stdout=DEVNULL, stderr=DEVNULL).wait()
        Popen(["git", "branch", "-u", "origin/dev", "dev"], stdout=DEVNULL, stderr=DEVNULL).wait()
        Popen(["git", "remote", "update"], stdout=DEVNULL, stderr=DEVNULL).wait()

        if "Your branch is behind" in str(check_output(["git", "status", "-uno"]), "utf-8"):
            call(["git", "pull", "-f"])
            os.chdir(path=old_dir)
            print("")

            return True
        else:
            os.chdir(path=old_dir)
            print("%s is up to date. No need to pull." % project_dir)

            return False

    elif not os.path.exists(path=project_dir):
        call(["git", "clone", bitbucket_project_url, project_dir])

        old_dir: str = os.getcwd()
        os.chdir(path=project_dir)

        print("Working with %s ..." % project_dir)

        Popen(["git", "checkout", branch], stdout=DEVNULL, stderr=DEVNULL).wait()
        Popen(["git", "branch", "-u", "origin/dev", "dev"], stdout=DEVNULL, stderr=DEVNULL).wait()
        os.chdir(path=old_dir)
        print("")

        return True
    else:
        __die("%s already exists, but is not a directory. Cannot clone anything into it." % project_dir)

        return False


def __build_and_install_dependency_if_necessary(maven_data: dict, workspace: str, maven_repo_dir: str, new_version: bool, version: str) -> None:
    gid: str = maven_data["gid"]
    aid: str = maven_data["aid"]
    jar_path: str = os.path.join(maven_repo_dir, gid.replace(".", "/"), aid, version, aid + "-" + version + ".jar")
    pom_path: str = os.path.join(maven_repo_dir, gid.replace(".", "/"), aid, version, aid + "-" + version + ".pom")

    if os.path.isfile(path=jar_path) and os.path.isfile(path=pom_path) and not new_version:
        print("%s is up to date. No need to (re)install it.\n" % jar_path)

        return
    elif new_version:
        shutil.rmtree(path=os.path.dirname(jar_path))

    __create_dir_if_necessary(path=os.path.dirname(jar_path), mode=0o644)

    project_dir: str = os.path.join(workspace, aid)
    old_dir: str = os.getcwd()
    os.chdir(path=project_dir)

    print("Working with %s ..." % project_dir)

    call(["./build.sh"])
    call(["./mvn_local_install.sh"])
    os.chdir(path=old_dir)

    print("%s is now up to date.\n" % jar_path)


def __get_and_compile_mvc_projects(working_dir: str, branch: str="master") -> None:
    print("###############################################")
    print("# MVC Projects Fetching And Compiling Section #")
    print("###############################################\n")

    projects: list[dict[str: str]] = [
        {"gid": "uk.ac.rhul.cs.dice.vacuumworld", "aid": "vacuumworld", "name": "vacuumworld-3.0", "jar_name": "vw3model.jar"},
        {"gid": "uk.ac.rhul.cs.dice.vacuumworld", "aid": "vacuumworld-controller", "name": "vacuumworld-3.0-controller", "jar_name": "vw3controller.jar"},
        {"gid": "uk.ac.rhul.cs.dice.vacuumworld", "aid": "vacuumworldgui", "name": "vacuumworldgui", "jar_name": "vw3gui.jar"}
    ]

    workspace: str = os.path.join(working_dir, "workspace")
    __create_dir_if_necessary(path=workspace)

    for project in projects:
        new_version: bool = __clone_or_pull_project(maven_data=project, workspace=workspace, branch=branch)
        __build_project_if_necessary(maven_data=project, working_dir=working_dir, workspace=workspace, new_version=new_version)
        __setup_scripts_if_necessary(maven_data=project, working_dir=working_dir, workspace=workspace)


def __remove_or_unlink_resource_if_necessary(file_path: str) -> None:
    if not os.path.exists(jar_path):
        return
    elif os.is_symlink(jar_path):
    	os.unlink(jar_path)
    else:
        os.remove(jar_path)


def __build_project_if_necessary(maven_data: dict, working_dir: str, workspace: str, new_version: bool) -> None:
    jar_path: str = os.path.join(working_dir, maven_data["jar_name"])

    if new_version or not os.path.exists(jar_path):
        __remove_or_unlink_resource_if_necessary(file_path=jar_path)

        project_dir: str = os.path.join(workspace, maven_data["name"])
        old_dir: str = os.getcwd()
        os.chdir(path=project_dir)
        call(["./build.sh"])
        os.symlink(src=os.path.join(project_dir, "target", maven_data["jar_name"]), dst=jar_path)
        os.chdir(path=old_dir)

        print("%s is now up to date.\n" % jar_path)
    else:
        print("%s is already up to date.\n" % jar_path)


def __setup_scripts_if_necessary(maven_data: dict, working_dir: str, workspace: str) -> None:
    scripts_dir : str = os.path.join(workspace, maven_data["name"], "src", "main", "resources", "scripts")

    if os.path.isdir(s=scripts_dir):
    	__setup_scripts(working_dir=working_dir, scripts_dir=scripts_dir)


def __setup_scripts(working_dir: str, scripts_dir: str) -> None:
    print("########################################")
    print("# Additional Scripts Setup Sub-section #")
    print("########################################\n")

    for dir, subdir, files in os.walk(scripts_dir):
        for f in files:
            src_path: str = os.path.join(scripts_dir, f)
            target_path: str = os.path.join(working_dir, f)

            if not os.path.exists(path=target_path):
                print("Creating a symlink %s -> %s ..." % (src_path, target_path))
                os.symlink(src=src_path, dst=target_path)

                if target_path.endswith(".sh"):
                    os.chmod(path=target_path, mode=0o700)
                else:
                    os.chmod(path=target_path, mode=0o644)

                print("Done.\n")
            else:
                print("%s already exists. No need to create symlinks.\n" % target_path)

    print("#######################################################")
    print("# Back To MVC Projects Fetching And Compiling Section #")
    print("#######################################################\n")


def __finish_installation() -> None:
    print("Installation complete!")


def __die(message: str) -> None:
    print(message)
    sys.exit(1)


def main() -> None:
    __check_for_mandatory_software()
    java_version: str = __check_for_java_version()
    __check_for_javac_version(java_version=java_version)

    bitbucket_public_project_url: str = "https://bitbucket.org/rhul-dice-privacy/vacuumworld-3.0-public/downloads"

    __setup_ssh(bitbucket_public_project_url=bitbucket_public_project_url)

    working_dir: str = os.getcwd()

    __setup_maven_dependencies(working_dir=working_dir, branch="dev")
    __get_and_compile_mvc_projects(working_dir=working_dir, branch="dev")
    __finish_installation()


if __name__ == "__main__":
    main()

END
