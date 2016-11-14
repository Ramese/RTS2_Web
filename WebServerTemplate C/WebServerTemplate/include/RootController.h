#pragma once

#include "../include/BaseController.h"

class RootController : public BaseController
{
	
public:
	RootController();

	virtual void handleRequest(HTTPServerRequest &req, HTTPServerResponse &resp);

	static int count;
};