#!/usr/bin/python3

from argparse import ArgumentParser


lines = [
    '<?xml version="1.0" encoding="UTF-8" standalone="no"?>',
    '<project default="build-vacuumworld-task" name="Create Runnable Jar for Project vacuumworld-3.0">',
    "\t" + '<property name="dir.buildfile" value="."/>',
    "\t" + '<property name="dir.workspace" value="${dir.buildfile}/.."/>',
    "\t" + '<property name="dir.jarfile" value="[HOME_PATH_HERE]"/>',
    "\t" + '<target name="build-vacuumworld-task">',
    "\t\t" + '<mkdir dir="${dir.buildfile}/target/classes/imgs"/>',
    "\t\t" + '<mkdir dir="${dir.buildfile}/target/classes/META-INF/maven/uk.ac.rhul.cs.dice/vacuumworld-3.0"/>',
    "\t\t" + '<javac includeantruntime="false" srcdir="${dir.buildfile}/src/main/java" classpath="${dir.jarfile}/.m2/repository/uk/ac/rhul/dice/starworlds-lite/1.0.1-SNAPSHOT/starworlds-lite-1.0.1-SNAPSHOT.jar" destdir="${dir.buildfile}/target/classes"/>',
    "\t\t" + '<copy todir="${dir.buildfile}/target/classes/imgs">',
    "\t\t\t" + '<fileset dir="${dir.buildfile}/res/imgs"/>',
    "\t\t" + '</copy>',
    "\t\t" + '<copy file="${dir.buildfile}/pom.xml" tofile="${dir.buildfile}/target/classes/META-INF/maven/uk.ac.rhul.dice/vacuumworld-2.0/pom.xml"/>',
    "\t\t" + '<jar destfile="${dir.jarfile}/[JAR_NAME_HERE]" filesetmanifest="mergewithoutmain">',
    "\t\t\t" + '<manifest>',
    "\t\t\t\t" + '<attribute name="Main-Class" value="uk.ac.rhul.cs.dice.vacuumworld.VacuumWorld"/>',
    "\t\t\t\t" + '<attribute name="Class-Path" value="."/>',
    "\t\t\t" + '</manifest>',
    "\t\t\t" + '<fileset dir="${dir.buildfile}/target/classes"/>',
    "\t\t\t" + '<zipfileset excludes="META-INF/*.SF" src="${dir.jarfile}/.m2/repository/org/junit/jupiter/junit-jupiter-api/5.0.0/junit-jupiter-api-5.0.0.jar"/>',
    "\t\t\t" + '<zipfileset excludes="META-INF/*.SF" src="${dir.jarfile}/.m2/repository/org/apiguardian/apiguardian-api/1.0.0/apiguardian-api-1.0.0.jar"/>',
    "\t\t\t" + '<zipfileset excludes="META-INF/*.SF" src="${dir.jarfile}/.m2/repository/org/opentest4j/opentest4j/1.0.0/opentest4j-1.0.0.jar"/>',
    "\t\t\t" + '<zipfileset excludes="META-INF/*.SF" src="${dir.jarfile}/.m2/repository/org/junit/platform/junit-platform-commons/1.0.0/junit-platform-commons-1.0.0.jar"/>',
    "\t\t\t" + '<zipfileset excludes="META-INF/*.SF" src="${dir.jarfile}/.m2/repository/org/junit/platform/junit-platform-engine/1.0.0-M5/junit-platform-engine-1.0.0-M5.jar"/>',
    "\t\t\t" + '<zipfileset excludes="META-INF/*.SF" src="${dir.jarfile}/.m2/repository/org/junit/vintage/junit-vintage-engine/4.12.0/junit-vintage-engine-4.12.0.jar"/>',
    "\t\t\t" + '<zipfileset excludes="META-INF/*.SF" src="${dir.jarfile}/.m2/repository/junit/junit/4.12/junit-4.12.jar"/>',
    "\t\t\t" + '<zipfileset excludes="META-INF/*.SF" src="${dir.jarfile}/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar"/>',
    "\t\t\t" + '<zipfileset excludes="META-INF/*.SF" src="${dir.jarfile}/.m2/repository/uk/ac/rhul/dice/starworlds-lite/1.0.1-SNAPSHOT/starworlds-lite-1.0.1-SNAPSHOT.jar"/>',
    "\t\t" + '</jar>',
    "\t" + '</target>',
    '</project>'
]


def __parse_args():
    parser = ArgumentParser(description="ANT script generator for VacuumWorld")
    parser.add_argument('-H', '--home', required=True, metavar='<home-path>', type=str, action='store',
                        help='Home path')
    parser.add_argument('-a', '--ant', required=True, metavar='<ant-script-path>', type=str, action='store',
                        help='ANT script path')
    parser.add_argument('-j', '--jar', required=True, metavar='<jar-name>', type=str, action='store',
                        help='JAR name')

    args = parser.parse_args()

    return args.home, args.ant, args.jar


def build_ant_script():
    home_path, ant_path, jar_name = __parse_args()

    print("ANT script generator: using %s as home path." % home_path)
    print("ANT script generator: using %s as ANT script path." % ant_path)
    print("ANT script generator: using %s as JAR name." % jar_name)

    tokens = lines[4].split("[HOME_PATH_HERE]")
    lines[4] = tokens[0] + home_path + tokens[1]
    tokens = lines[13].split("[JAR_NAME_HERE]")
    lines[13] = tokens[0] + jar_name + tokens[1]

    with open(ant_path, "w") as dst:
        for line in lines:
            dst.write(line + "\n")


if __name__ == "__main__":
    build_ant_script()
