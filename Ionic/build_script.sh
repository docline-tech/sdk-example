while getopts t:p: flag
do
    case "${flag}" in
        t) type=${OPTARG};;
        p) platform=${OPTARG};;
    esac
done

platformLowercase=$(echo "$platform" | tr '[:upper:]' '[:lower:]')

# Remove the platform/ios folder or the platform/android folder depending on the platform where we want to test the changes.
rm -r "platforms/$platformLowercase"

# Remove node_modules/cordova-plugin-docline-sdk folder
sudo rm -Rf node_modules/cordova-plugin-docline-sdk

# Copy plugins/cordova-plugin-docline-sdk to node_modules/cordova-plugin-docline-sdk
cp -Rf plugins/cordova-plugin-docline-sdk node_modules/cordova-plugin-docline-sdk

typeLowercase=$(echo "$type" | tr '[:upper:]' '[:lower:]')

if [ "$typeLowercase" == "ionic" ];
then
    # Run
    ionic cordova run
else
    # Add platform iOS/Android
    cordova platform add $platformLowercase
    # Run
    cordova run
fi