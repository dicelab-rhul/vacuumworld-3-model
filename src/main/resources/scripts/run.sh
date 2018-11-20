#!/bin/bash

KONSOLE="konsole"
GNOME_TERMINAL="gnome-terminal"
XFCE_TERMINAL="xfce4-terminal"
KDE_OK=$(which "$KONSOLE")
GNOME_OK=$(which "$GNOME_TERMINAL")
XFCE_OK=$(which "$XFCE_TERMINAL")
MODEL_SCRIPT="model.sh"
CONTROLLER_SCRIPT="controller.sh"
VIEW_SCRIPT="view.sh"
MODEL_SCRIPT_CMD=./$MODEL_SCRIPT
CONTROLLER_SCRIPT_CMD=./$CONTROLLER_SCRIPT
VIEW_SCRIPT_CMD=./$VIEW_SCRIPT
SLEEP_TIME=0.2
KILLALL=killall
SIGNAL=SIGKILL

for COMPONENT_SCRIPT in $MODEL_SCRIPT $CONTROLLER_SCRIPT $VIEW_SCRIPT; do
    $KILLALL -q -s $SIGNAL $COMPONENT_SCRIPT
done

if [ "" != "$KDE_OK" ]; then
    echo "$KONSOLE found! Running as KDE..."
    $KONSOLE --workdir $(pwd) -e $MODEL_SCRIPT_CMD &
    $KONSOLE --workdir $(pwd) -e $CONTROLLER_SCRIPT_CMD &
    sleep $SLEEP_TIME
    $KONSOLE --workdir $(pwd) -e $VIEW_SCRIPT_CMD # We do not need to detach it with &
elif [ "" != "$GNOME_OK" ]; then
    echo "$GNOME_TERMINAL found! Running as GNOME..."
    $GNOME_TERMINAL -- $MODEL_SCRIPT_CMD &
    $GNOME_TERMINAL -- $CONTROLLER_SCRIPT_CMD &
    sleep $SLEEP_TIME
    $GNOME_TERMINAL -- $VIEW_SCRIPT_CMD # We do not need to detach it with &
elif [ "" != "XFCE_OK" ]; then
    echo "$XFCE_TERMINAL found! Running as XFCE..."
    $XFCE_TERMINAL -x $MODEL_SCRIPT_CMD &
    $XFCE_TERMINAL -x $CONTROLLER_SCRIPT_CMD &
    sleep $SLEEP_TIME
    $XFCE_TERMINAL -x $VIEW_SCRIPT_CMD # We do not need to detach it with &
else
    echo "Could not find a suitable terminal. You have to run $MODEL_SCRIPT_CMD, $CONTROLLER_SCRIPT_CMD, and $VIEW_SCRIPT_CMD (in this order) in three separate terminal tabs."
fi 
