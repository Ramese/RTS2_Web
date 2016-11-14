#pragma once

#include "../include/BaseController.h"

class TestController : public BaseController
{

public:
	TestController();

	virtual void handleRequest(HTTPServerRequest &req, HTTPServerResponse &resp);

	static int count;
};