### Mock Interceptor

Return JSON file in local folder instead of real response from network 

# How to use:

With **GET** request to url "http://domain.com/api/all"

1. Create folder **domain.com/api** in somewhere in your **:app** module directory (for example: inside **/assets** folder)
2. Create JSON file **GET_all.json** inside that folder and put your fake data response in there
3. Implement **ResourceHelper** to get InputStream from device file (see **AppResourceHelper**)
4. Add new **MockInterceptor** instance to OkHttpClient (see **MainActivity**)