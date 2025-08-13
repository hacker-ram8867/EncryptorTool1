import hashlib
import os
import sys
from Crypto.Cipher import AES
from Crypto.Util.Padding import pad, unpad
from getpass import getpass
import random

# --- Helper Functions ---

def get_key_from_password(password):
    sha = hashlib.sha256()
    sha.update(password.encode('utf-8'))
    return sha.digest()  # 256-bit key for AES-256


def encrypt_file(input_path, output_path, key):
    cipher = AES.new(key, AES.MODE_CBC)
    iv = cipher.iv

    with open(input_path, 'rb') as f_in:
        data = f_in.read()
    padded_data = pad(data, AES.block_size)

    encrypted_data = cipher.encrypt(padded_data)

    with open(output_path, 'wb') as f_out:
        f_out.write(iv + encrypted_data)  # Prepend IV


def decrypt_file(input_path, output_path, key):
    with open(input_path, 'rb') as f_in:
        iv = f_in.read(16)
        encrypted_data = f_in.read()

    cipher = AES.new(key, AES.MODE_CBC, iv)

    decrypted_padded = cipher.decrypt(encrypted_data)
    data = unpad(decrypted_padded, AES.block_size)

    with open(output_path, 'wb') as f_out:
        f_out.write(data)


def corrupt_file(file_path):
    try:
        with open(file_path, 'wb') as f:
            for _ in range(10):
                f.write(os.urandom(1024))
        print("☠️ Your file was corrupted.")
    except Exception as e:
        print(f"⚠️ Failed to corrupt file: {e}")


# --- Main Program ---

def main():
    print("🔒 Enter 'E' to Encrypt or 'D' to Decrypt")
    choice = input("Choice (E/D): ").strip().upper()

    input_path = input("Enter path of file: ").strip()
    output_path = input("Enter output file path: ").strip()

    if not os.path.exists(input_path):
        print("❌ Input file does not exist.")
        sys.exit()

    if choice == 'E':
        password = getpass("Enter password to encrypt: ")
        key = get_key_from_password(password)
        try:
            encrypt_file(input_path, output_path, key)
            print(f"✅ File encrypted successfully: {output_path}")
        except Exception as e:
            print(f"⚠️ Encryption failed: {e}")

    elif choice == 'D':
        attempts = 0
        success = False

        while attempts < 5 and not success:
            password = getpass(f"Enter password to decrypt (Attempt {attempts+1}/5): ")
            key = get_key_from_password(password)

            try:
                decrypt_file(input_path, output_path, key)
                print(f"✅ File decrypted successfully: {output_path}")
                success = True
            except Exception:
                print("❌ Incorrect password or corrupted file.")
                attempts += 1

        if not success:
            print("💀 Too many failed attempts.")
            corrupt_file(input_path)

    else:
        print("❌ Invalid choice. Choose 'E' or 'D'.")

if __name__ == "__main__":
    main()
