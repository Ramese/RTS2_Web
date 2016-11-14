#include "include/WebServer.h"

#include <iostream>
#include <string>
#include <vector>

int main(int argc, char** argv)
{
	WebServer app;
	return app.run(argc, argv);
}