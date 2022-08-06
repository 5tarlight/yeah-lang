./gradlew build
echo ""
java -jar ./build/libs/$(ls ./build/libs) "$@"