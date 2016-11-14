#pragma once

#include <Poco/Net/HTTPRequestHandler.h>
#include <Poco/Net/HTTPResponse.h>
#include <Poco/Net/HTTPServerRequest.h>
#include <Poco/Net/HTTPServerResponse.h>

using namespace Poco::Net;
using namespace std;

class BaseController : public HTTPRequestHandler
{
public:
	string path;
	void handleRequest(HTTPServerRequest &req, HTTPServerResponse &resp);
	void virtual ControllerMethod(HTTPServerRequest &req, HTTPServerResponse &resp, string user);
};