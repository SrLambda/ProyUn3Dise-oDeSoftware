import subprocess
import sys
import threading
import time
import webbrowser


def run_in_new_terminal(command: str):
    """
    Runs a command in a new, separate terminal window.
    This is perfect for monitoring server logs.
    """
    try:
        if sys.platform == "win32":
            # For Windows: The /k flag keeps the window open
            subprocess.run(f'start powershell -NoExit -Command "{command}"', shell=True)
        elif sys.platform == "darwin":
            # For macOS: Launches a new Terminal instance
            subprocess.run(
                ["open", "-a", "Terminal.app", "-n", "--args", "bash", "-c", command]
            )
        else:
            # For Linux (e.g., Ubuntu with GNOME Terminal)
            # You might need to change 'gnome-terminal' to your terminal (e.g., 'konsole', 'xterm')
            subprocess.run(["kitty", "--", "bash", "-c", command])
    except Exception as e:
        print(f"Error: Failed to open new terminal for command '{command}'. {e}")
        print(
            "Please ensure your terminal emulator (e.g., gnome-terminal) is installed."
        )


# --- Main Program ---
if __name__ == "__main__":
    # List of commands to start your Spring Boot and npm services
    # The '&&' ensures the second command runs only if the 'cd' is successful
    commands_to_run = [
        "cd orders-service && mvn spring-boot:run",
        "cd ratings-service && mvn spring-boot:run",
        "cd stores-service && mvn spring-boot:run",
        "cd users-service && mvn spring-boot:run",
        "cd utaleats-frontend && npm start",
    ]

    threads = []

    print("🚀 Launching 5 services in new terminal windows...")

    for cmd in commands_to_run:
        thread = threading.Thread(target=run_in_new_terminal, args=(cmd,))
        threads.append(thread)
        thread.start()
        # A small delay to prevent all windows from launching at the exact same instant
        time.sleep(0.5)

    print("✅ All service terminals have been launched.")
    print("Check the new windows for server logs.")

    try:
        frontend_url = "http://localhost:5173/"
        print(f"\nWaiting 5 seconds before opening the frontend...")
        time.sleep(10)
        print(f"Opening browser at {frontend_url} 🌐")
        webbrowser.open(frontend_url)
    except Exception as e:
        print(f"Could not open web browser: {e}")

    for thread in threads:
        thread.join()
