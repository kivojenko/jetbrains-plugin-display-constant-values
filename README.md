# Display Constant Values Plugin  

A lightweight and intuitive IntelliJ IDEA plugin designed to enhance the readability of Python code by folding constants in a clear and meaningful way.  

## Key Features  

- **Enumerated constants** are folded to their **names** for clarity.  
- **Uppercase constants** (e.g., `MY_CONSTANT`) are folded to their **values**.  

## How It Works  

This plugin folds constants in the following ways:  

1. **Enumerated Constants**  
  ```python  
  class Color(Enum):  
    RED = 1  
    GREEN = 2  
    BLUE = 3  

  # Color.RED -> RED
  ```  

2. **Uppercase Constants**  
  ```python
  MY_CONSTANT = 42  

  # MY_CONSTANT -> 42
  ```  

## Installation  

You can install this plugin directly from the **IntelliJ Plugin Marketplace**:  

1. Open PyCharm or another JetBrains IDE.  
2. Go to `File > Settings > Plugins`.  
3. Search for **"Display Constant Values"** in the Marketplace tab.  
4. Click **Install** and restart IDE.  

## Change Log  

- **v1.0.4**: Improved support for enums.  
- **v1.0.3**: Added folding for all enumerated constants to their names.  
- **v1.0.2**: Enabled folding persistence across file reopen events.  
- **v1.0.1**: Updated to support IntelliJ IDEA 2024.  
- **v1.0.0**: Initial release with core functionality.  

## Feedback and Contributions  

Your feedback is essential for improving this plugin!  

### Ways to Get Involved:  
- **Report Bugs**: If you encounter any issues, please create a bug report in this repository’s [issues section](#).  
- **Request Features**: Have an idea to make this plugin even better? Submit a feature request!  
- **Contribute Code**: Pull requests are always welcome if you'd like to contribute directly.  

### Contact  

For any questions or direct feedback, feel free to reach me at:  
📧 **kivojenko@gmail.com**  

## License  

This plugin is open-source and licensed under the [Apache License 2.0](LICENSE).  

---

Thanks for checking out! 🚀  
