#!/bin/bash

DEFAULT_PYTHON3=$(/usr/bin/env python3 --version |& cut -d' ' -f2)
MAJOR=$(echo ${DEFAULT_PYTHON3} | cut -d'.' -f1)
MINOR=$(echo ${DEFAULT_PYTHON3} | cut -d'.' -f2)

if [[ ${MAJOR} < "3" ]]; then
    echo "Python 3.6+ is required. Aborting..."

    exit 1
fi

if [[ ${MINOR} -lt "6" ]]; then
    echo "Python 3.6+ is required. Aborting..."

    exit 1
fi

/usr/bin/env python3 - <<END

import os
import shutil


from subprocess import Popen, call, check_output, DEVNULL


def __remove_or_unlink_resource_if_necessary(file_path: str) -> None:
    if os.path.islink(file_path):
        print("%s exists and is a symlink. Unlinking it.." % file_path)
        os.unlink(file_path)
        print("Done.\n")
    elif not os.path.exists(file_path):
        print("%s does not exist. No need to remove it or unlink it.\n" % file_path)
    else:
        print("%s exists and is a not symlink Removing it.." % file_path)
        shutil.rmtree(path=file_path)
        print("Done.\n")


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


def main() -> None:
    print("####################################")
    print("# MVC Projects Recompiling Section #")
    print("####################################\n")

    projects: list[dict[str: str]] = [
        {"gid": "uk.ac.rhul.cs.dice.vacuumworld", "aid": "vacuumworld", "name": "vacuumworld-3.0", "jar_name": "vw3model.jar"},
        {"gid": "uk.ac.rhul.cs.dice.vacuumworld", "aid": "vacuumworld-controller", "name": "vacuumworld-3.0-controller", "jar_name": "vw3controller.jar"},
        {"gid": "uk.ac.rhul.cs.dice.vacuumworld", "aid": "vacuumworldgui", "name": "vacuumworldgui", "jar_name": "vw3gui.jar"}
    ]

    working_dir: str = os.getcwd()
    workspace: str = os.path.join(working_dir, "workspace")

    for project in projects:
        __build_project_if_necessary(maven_data=project, working_dir=working_dir, workspace=workspace, new_version=True)


if __name__ == "__main__":
    main()

END
