while getopts p: flag
do
    case "${flag}" in
        p) platform=${OPTARG};;
    esac
done

platformLowercase=$(echo "$platform" | tr '[:upper:]' '[:lower:]')

# Install
npm install    
# Build
npm run build
# Run
ionic capacitor run "$platformLowercase"
