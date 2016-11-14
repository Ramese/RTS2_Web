#pragma once

#include <Poco/Net/HTTPServer.h>
#include <Poco/Util/ServerApplication.h>

#include <iostream>
#include <string>
#include <vector>

/*using namespace Poco::Net;*/
using namespace Poco::Util;
using namespace std;

class WebServer : public ServerApplication
{
public:
	WebServer();
	~WebServer();
protected:
	int main(const vector<string> &);
};

