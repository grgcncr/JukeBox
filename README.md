# JukeBox
A very simple app that plays music

# How to Compile and Run the Project

## Step 1: Navigate to the Project Folder
Navigate to the project folder (`JavaProject`) using the terminal or command prompt.

## Step 2: Compile the Project
Run the following command to compile the project using Maven:
```bash
mvn package
```

This will create a `.jar` file named `*-jar-with-dependencies.jar` in the `target` folder. All required dependencies are bundled into this `.jar` file.

## Step 3: Run the Project
To run the project, navigate to the `target` folder and execute the following command in the terminal:
```bash
java -jar <filename>-jar-with-dependencies.jar
```

### Example:
```bash
java -jar JukeBoxProject-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```

You're all set! 🎉

