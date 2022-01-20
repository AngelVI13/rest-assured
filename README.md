# To run the tests
* Make sure you have JDK installed (and added to PATH)
* Make sure you have Maven installed (and added to PATH)
* Make sure you have Allure installed (via scoop) (and added to PATH)
* Make sure you have a testrail project, and have the endpoint & credentials configured for it in PostResults.java - getTestRailAPIClient()
* Make sure you have a test run created in your testrail project and specify it in PostResult.java - post(..) 
* If you would like to run the tests locally (with mock data) - make sure you have python 3.8 and flask library installed. Prior to running the tests `step 1` below, you need to `cd mock_apis` and run `py -3.8 mock_api.py` or `python mock_api.py`.

1. Run `mvn clean test`
2. To view Allure results run `allure serve allure-results/`

