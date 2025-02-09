<Details>
<Summary>Assignment 1</Summary>

# Reflection 1
I built two new features using Spring Boot: a delete product feature and an edit product feature. 
In the delete feature, when the user clicks the delete button, a confirmation dialog appears and then the ProductController calls the service to remove the product. 
For the edit feature, a form is displayed with the product's current details using Thymeleaf, and when the user submits the form, the product gets updated. 
I tried to follow clean code principles by splitting my code into different layers (Model, Repository, Service, and Controller), using clear method names like create, delete, getById, and update, and keeping my code modular and easy to understand. 
I also applied some secure coding practices by generating unique IDs with UUID and safely checking for null values to avoid errors. 
However, I think my code could be improved by adding better error handling with a global exception handler, using input validation annotations like @Valid to ensure user inputs are correct, and adding logging to help track what happens in the app. 
Overall, I believe my code is clean and follows basic security practices, but there is still room for improvement as I learn more.

</Details>
