#pragma once

#include <Poco/Net/HTTPRequestHandlerFactory.h>
#include <Poco/Net/HTTPResponse.h>

#include "HTTPConstants.h"
#include "RootController.h"

class WebServerRouting : public HTTPRequestHandlerFactory
{
public:
	WebServerRouting();
	~WebServerRouting();
	virtual HTTPRequestHandler* createRequestHandler(const HTTPServerRequest& request);
};

