## Next steps 
Come up with payload generation strategy
So if you need to test a middle point API then you can request a mock payload that needs to be used for testing the middle API. 

Example Endpoint Flow:

Login -> GetUserInfo -> GetTransactionsForUser -> etc ...

For example, we need the login cookies in order to be able to test the GetUserInfo endpoint.
Similarly we need the user info mock payload to be able to test GetTransactionsForUser.

Figure out how to handle, separate payloads for such example workflow so these can be used in individual API tests.

* Renewed banking & CU - flow pattern example. It was suggested that this example flow could be used for the payload management demo 

## TODOs

1. payload management
2. add test payload for each feature file
3. how to define/expect payload structure for each test/usage
