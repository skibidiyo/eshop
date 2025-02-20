<Details>
<Summary>Assignment 1</Summary>

# Reflection 1
I built two new features using Spring Boot: a delete product feature and an edit product feature. 
In the delete feature, when the user clicks the delete button, a confirmation dialog appears and then the ProductController calls the service to remove the product. 
For the edit feature, a form is displayed with the product's current details using Thymeleaf, and when the user submits the form, the product gets updated. 
I tried to follow clean code principles by splitting my code into different layers (Model, Repository, Service, and Controller), using clear method names like create, delete, getById, and update, and keeping my code modular and easy to understand. 
I also applied some secure coding practices by generating unique IDs with UUID and safely checking for null values to avoid errors. 
However, I think my code could be improved by adding better error handling with a global exception handler, using input validation annotations like @Valid to ensure user inputs are correct, and adding logging to help track what happens in the app. 


# Reflection 2
- After writing the unit tests, I feel more confident that im taking the right steps in testing my code and I understand that there is no exact number of unit tests that must be written.
  Although reaching high or even 100% code coverage is a positive sign, it doesnt mean our code is completely bug-free. 
  Code coverage simply shows which lines have been executed, not the overall quality or thoroughness of the tests.


- The new functional test suite is less clean because it duplicates the same setup procedures and instance variables from the previous tests, which violates the DRY principle and makes the tests harder to manage.
  A better approach is to extract the common setup code into a shared base class or helper, so all test suites can reuse the same code, making the overall test code cleaner and easier to update.



</Details>

<Details>

<Summary>Assignment 2</Summary>

# Reflection

- List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them.
  Issue : The string "redirect:/product/list" was repeated in multiple methods.\
  Strategy : I made a constant called REDIRECT_PRODUCT_LIST to store "redirect:/product/list", so I only write it once and dont have to repeat it all over the code\
  Issue : Remove the declaration of thrown exception 'java.lang.Exception', as it cannot be thrown from methods body in HomePageFunctionalTest\
  Strategy : I removed the unnecessary throws Exception clauses to keep the code clean and simple\
  Issue : Remove this unused import 'org.springframework.boot.test.mock.mockito.MockBean' in ProductRepositoryTest\
  Strategy : I removed the unused import\
  Issue : Add a nested comment explaining why this method is empty, throw an UnsupportedOperationException or complete the implementation in ProductRepositoryTest and EshopApplicationTests\
  Strategy : Added comments\
  Issue : Do not hardcode version numbers in build.gradle.kts\
  Strategy : I fixed the hardcoded version numbers by moving them into a central Gradle Version Catalog, so my build file now just references those versions



- Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)!\
  The CI workflows automatically build the project, run unit tests, and perform code quality and security analysis whenever code is pushed or a pull request is opened. 
  The deployment workflow automatically builds the Docker image and triggers a deployment to Koyeb on pushes to the main branch. 
  Additionally, the inclusion of scheduled and branch-protection checks further reinforces the reliability and security of the integration and deployment processes.





</Details>
