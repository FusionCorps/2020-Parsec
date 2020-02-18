export ROBOT_NAME := "Malevolent"
export ROBOT_TEAM := "6672"

# Format and build project
build: format
    gradle build

# Format, build, and deploy project
deploy: build
    just robot-connect && gradle deploy && just robot-disconnect

# Run the robot simulator
sim: build
    gradle simulateJava

# Format all code
format:
    ktlint -F "src/**/*.kt" --experimental

# Connect to the robot
robot-connect:
    #!/usr/bin/env sh
    export PREV_NWK=`nmcli d show wlp59s0 | rg GENERAL.CONNECTION | sed -n -e 's/^.*GENERAL.CONNECTION: //p' | awk '{$1=$1};1'`
    export PREV_PWD=`nmcli d wifi show-password | rg Security: | sed -n -e 's/^.*Security: //p' | awk '{$1=$1};1'`
    nmcli d wifi connect {{ROBOT_TEAM}}-{{ROBOT_NAME}}

# Disconnect from the robot
robot-disconnect:
    #!/usr/bin/env sh
    nmcli d wifi connect $PREV_NWK password $PREV_PWD
