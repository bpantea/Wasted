# Wasted

Mobile application. For the backend check out: https://github.com/bpantea/wasted-backend

## Getting Started

The backend is deployed on https://wasted-server.herokuapp.com/

In the application you can select the instance of the backend server (if you want the localhost one or the staging one) in the RetrofitProvider class for baseUrl (for the moment, line 30) R.string.api_server_staging or R.string.api_server_local

If you select the local one you must have a running instance of wasted-backend and put your local address in strings.xml (res/values) where api_server_local is. And also put the port of the server (8080 default)
