#include "include/WebServer.h"
#include "include/WebServerRouting.h"

WebServer::WebServer()
{
}

WebServer::~WebServer()
{
}

int WebServer::main(const vector<string> &)
{
	HTTPServer server(new WebServerRouting, ServerSocket(9090), new HTTPServerParams);

	server.start();
	cout << endl << "Server started" << endl;

	waitForTerminationRequest();  // wait for CTRL-C or kill

	cout << endl << "Shutting down..." << endl;
	server.stop();

	return Application::EXIT_OK;
}