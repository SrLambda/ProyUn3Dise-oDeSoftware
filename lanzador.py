import subprocess
import sys
import threading
import time
import webbrowser


def run_in_new_terminal(directory: str, command: str):
    """
    Builds the OS-specific command and runs it in a new terminal.
    - Windows: Uses PowerShell with ';' to chain commands.
    - Linux/macOS: Uses bash with '&&' to chain commands.
    """
    # Build the final command string based on the OS
    if sys.platform == "win32":
        # PowerShell uses a semicolon ';' to separate commands
        final_command = f"cd {directory}; {command}"
        # Use 'start powershell' to launch a new window
        shell_command = f'start powershell -NoExit -Command "{final_command}"'
        subprocess.run(shell_command, shell=True)
    else:
        # Linux & macOS use '&&' for safer command chaining
        final_command = f"cd {directory} && {command}"
        try:
            if sys.platform == "darwin":  # macOS
                subprocess.run(
                    [
                        "open",
                        "-a",
                        "Terminal.app",
                        "-n",
                        "--args",
                        "bash",
                        "-c",
                        final_command,
                    ]
                )
            else:  # Linux
                subprocess.run(
                    [
                        "kitty",
                        "-e",
                        f'bash -c "{final_command}; exec bash"',
                    ]
                )
        except FileNotFoundError:
            print("Error: Could not find a terminal to launch.")


# --- Main Program ---
if __name__ == "__main__":
    # Commands are now defined by directory and the process to run
    services_to_run = [
        {"dir": "orders-service", "cmd": "mvn spring-boot:run"},
        {"dir": "ratings-service", "cmd": "mvn spring-boot:run"},
        {"dir": "stores-service", "cmd": "mvn spring-boot:run"},
        {"dir": "users-service", "cmd": "mvn spring-boot:run"},
        {"dir": "utaleats-frontend", "cmd": "npm start"},
    ]

    threads = []

    print("🚀 Launching 5 services in new terminal windows...")

    for service in services_to_run:
        # Pass the directory and command separately
        thread = threading.Thread(
            target=run_in_new_terminal, args=(service["dir"], service["cmd"])
        )
        threads.append(thread)
        thread.start()
        time.sleep(0.5)

    for thread in threads:
        thread.join()

    print("✅ All service terminals have been launched.")

    # --- Wait 5 seconds and open the browser ---
    try:
        frontend_url = "http://localhost:5173/"
        print(f"\nWaiting 5 seconds before opening the frontend...")
        time.sleep(10)
        print(f"Opening browser at {frontend_url} 🌐")
        webbrowser.open(frontend_url)
    except Exception as e:
        print(f"Could not open web browser: {e}")
