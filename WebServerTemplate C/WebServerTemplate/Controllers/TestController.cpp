#include "../include/TestController.h"
#include "../include/HTTPConstants.h"

int TestController::count;

TestController::TestController() {
	path = "/test/";
}

void TestController::handleRequest(HTTPServerRequest &req, HTTPServerResponse &resp)
{
	try {
		resp.setStatus(HTTPResponse::HTTP_OK);
		resp.setContentType(HTTP_HEADER_CONTENT_TYPE_HTML);

		ostream& out = resp.send();
		out << "<h1>Hello world!</h1>"
			<< "<p>Count: " << count << "</p>"
			<< "<p>Host: " << req.getHost() << "</p>"
			<< "<p>Method: " << req.getMethod() << "</p>"
			<< "<p>URI: " << req.getURI() << "</p>";
		out.flush();

		cout << endl
			<< "Response sent for count=" << count
			<< " and URI=" << req.getURI() << endl;

		count++;
	}
	catch (exception e) {
		resp.setStatus(HTTPResponse::HTTP_EXPECTATION_FAILED);
		resp.setContentType(HTTP_HEADER_CONTENT_TYPE_HTML);
	}
}