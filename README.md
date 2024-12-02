# Task Tracker GUI

A GUI application to track and manage tasks, developed in **Java** as part of the based on [CLI version](https://github.com/jmsmarcelo/task-tracker-cli-java) as part of the [Task Tracker Project](https://roadmap.sh/projects/task-tracker) from Roadmap.sh.

## 📋 Features
- Add, Update, and Delete tasks
- Mark a task as in progress or done
- List all tasks
- List all tasks are not done
- List all tasks are in progress
- List all tasks are done

## ⚙️ Technologies and Tools

- Language: **Java**
- GUI Structure: **JavaFX**
- Data Persistence: Store the tasks in a JSON file

## 🛠️ How to Run the Project

### Prerequisites

- **Java 17** or later installed
- **JavaFX 17** or later
- Read/write permission

### Steps to Execute

1. Clone the repository:
  ```bash
  git clone https://github.com/jmsmarcelo/task-tracker-javafx.git
  cd task-tracker-javafx
  ```

2. Add JavaFX SDK
   ```bash
   # Download and extract JavaFX SDK to task-tracker-javafx
   # Download page: https://gluonhq.com/products/javafx/
   ```

3. Compile the project (Windows):
   ```bash
   mkdir bin
   javac --module-path javafx-sdk*\lib --add-modules javafx.controls -d bin src\*.java
   ```
4. Run the project (Windows:
   ```bash
   cd bin
   start "" javaw --module-path ..\javafx-sdk*\lib --add-modules javafx.controls TaskApp
   exit
   ```
Alternatively, use automatic compile and run
   - Windows
     - Double click in `win_compile.bat` `win_run.bat`
     
   - Linux/MacOS
     ```bash
     # Make scripts executable
     chmod +x unix_compile.sh unix_run.sh

     # Run the scripts
     ./unix_compile.sh
     ./unix_run.sh
     ```

## 📦 Project Structure
```plaintext
task-tracker-javafx/
└─ src/
   ├─ TaskApp.java         # Application GUI
   ├─ Task.java            # Task domain model
   ├─ TaskStatus.java      # Enum task status
   ├─ TaskService.java     # Task service logic
   └─ TaskRepository.java  # file persistence handling
```
## 🖼️ Screenshots
![Screenshot 2024-12-01 090750](https://github.com/user-attachments/assets/2af3791e-3b37-4efe-ae96-39cb09419fde)
![Screenshot 2024-12-01 090811](https://github.com/user-attachments/assets/aca9928d-332d-4982-a2f7-1b1244814928)
![Screenshot 2024-12-01 090908](https://github.com/user-attachments/assets/8e714d04-dd12-470b-b0f4-ccd83341b0fa)

## 📖 How to Contribute

Contributions are welcome!

To contribute:
1. Fork the repository.
2. Create a new branch:
   ```bash
   git checkout -b my-new-feature
   ```
3. Commit your changes:
   ```bash
   git commit -m "add new feature"
   ```
4. Push to the branch:
   ```bash
   git push origin my-new-feature
   ```
5. Open a Pull Request.

## 📜 License
This project is licensed under the **MIT License**. See the [LICENSE](https://github.com/jmsmarcelo/task-tracker-javafx/blob/main/LICENSE) file for details.

## 🙋‍♂️ Author
Created by [Jose Marcelo](https://jmsmarcelo.github.io/). Feel free to get in touch or explore more of my projects!
