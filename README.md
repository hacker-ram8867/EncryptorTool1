🔐 Java File Encryption/Decryption Tool
This is a simple Java-based command-line tool that allows you to securely encrypt and decrypt files using AES encryption. It uses a password to derive a secure key and includes a feature to corrupt files if decryption is attempted too many times with the wrong password.

📁 Features
🔒 AES Encryption using javax.crypto

🔓 Secure decryption with password validation

💣 Corrupts file after 5 failed decryption attempts

📦 No external libraries required

💻 Works in Eclipse, VS Code, or any Java environment

🛠 How to Run
✅ Option 1: Using Eclipse (Recommended)
Open Eclipse IDE

Create a new Java Project:

File > New > Java Project

Create a new package named:

FilesEncryptionPrograms
Create a new class named:

FileEncypter
Copy and paste the full code into the class

Run the program:

Right-click on the file > Run As > Java Application

Eclipse includes required Java libraries by default — no setup needed.

💻 Option 2: Using VS Code
Install prerequisites:

Java JDK 8 or later

VS Code with the Java Extension Pack

Create a Java project:

Open Command Palette (Ctrl+Shift+P)

Choose: Java: Create Java Project

Add the Java file:

Inside the src folder, create package FilesEncryptionPrograms

Add the FileEncypter.java file with the provided code

Compile and run:

javac FilesEncryptionPrograms/FileEncypter.java
java FilesEncryptionPrograms.FileEncypter
If javax.crypto appears missing, ensure you're using the JDK, not just the JRE.

📋 Example Usage
plaintext
Copy
Edit
🔒 Enter 'E' to encrypt or 'D' to decrypt: E
Enter path of file: /Users/you/Documents/test.txt
Enter output file path: /Users/you/Documents/test_encrypted.txt
Enter password to encrypt: mySecret123
✅ File encrypted successfully: /Users/you/Documents/test_encrypted.txt
plaintext
Copy
Edit
🔒 Enter 'E' to encrypt or 'D' to decrypt: D
Enter path of file: /Users/you/Documents/test_encrypted.txt
Enter output file path: /Users/you/Documents/test_decrypted.txt
Enter password to decrypt (Attempt 1/5): wrongpass
❌ Incorrect password or corrupted file.
...
💀 Too many failed attempts. The file will be corrupted.
☠️ Your file was corrupted.
🔧 Requirements
Java Development Kit (JDK 8 or higher)

No external libraries needed

Uses:

javax.crypto

java.security

java.io

java.util

⚠️ Important Notes
💾 Don't forget your password. There's no password recovery.

⛔ After 5 failed decryption attempts, the original file is intentionally corrupted.

🔑 Only the same password that was used to encrypt the file can decrypt it.

🔐 All operations are local — no data is sent anywhere.

📬 Contact
For questions, feedback, or contributions, reach out via:

📧 Email: hellohellohihi112233@gmail.com

🧑‍💻 GitHub: https://github.com/hacker-ram8867

🧑‍💻 Author
Developed for educational and secure file-handling purposes.
Feel free to modify and improve based on your needs.
