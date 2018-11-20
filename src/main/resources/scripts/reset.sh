#!/bin/bash

MY_DIR=$(pwd)
MODEL_DIR="vacuumworld-3.0"
INSTALL_DIR=${MY_DIR}
WORKSPACE=${INSTALL_DIR}/workspace
MY_HOME=$(eval echo ~$(whoami))
MVN_LOCAL_REPO=${MY_HOME}/.m2/repository
MVN_DEPS_AR_IDS=(agentactions agentcommon agentcontainers agentprototype LogUtilities vwcommon)
MVN_DEPS_GR_IDS=(uk.ac.rhul.cs.dice uk.ac.rhul.cs.dice uk.ac.rhul.cs.dice uk.ac.rhul.cs.dice org.cloudstrife9999 uk.ac.rhul.cs.dice.vacuumworld)
VERSION="1.0.1"

for ((i=0;i<${#MVN_DEPS_GR_IDS[@]};++i)); do
  GID=${MVN_DEPS_GR_IDS[i]}
  AID=${MVN_DEPS_AR_IDS[i]}
  PARENT_DIR=${GID//\./\/}/${AID}/${VERSION}/
  DEP_PATH=${MVN_LOCAL_REPO}/${PARENT_DIR}

  if [ -d "$DEP_PATH" ]; then
    echo Deleting ${DEP_PATH} ...
    rm -rf ${DEP_PATH}
    echo "Done."
  else
    echo "${DEP_PATH} not found (or it is not a directory). Not deleting it either way."
  fi

  echo ""
done

for ADDITIONAL in vw3model.jar model.sh vw3gui.jar view.sh vw3controller.jar controller.sh run.sh recompile.sh recompile_and_run.sh config.json minds.json random_state_generator.sh update_minds.sh reset.sh; do
  PATH_TO_CHECK=${INSTALL_DIR}/${ADDITIONAL}

  if [ -f ${PATH_TO_CHECK} ]; then
    echo "${PATH_TO_CHECK} found. Deleting it."
    rm -f "$PATH_TO_CHECK"
  else
    echo "${PATH_TO_CHECK} not found (or it is not a regular file). Not deleting it either way."
  fi

  echo ""
done

for PROJ in ${MODEL_DIR} vacuumworld-3.0-controller vacuumworldgui agentactions agentcommon agentcontainers agentprototype LogUtilities vwcommon; do
  TO_REMOVE=${WORKSPACE}/${PROJ}

  echo "Removing ${TO_REMOVE} ..."
  rm -rf ${TO_REMOVE}
  echo "Done."
  echo ""
done

echo "END OF THE CLEANING PROCEDURE."
