#include "include/WebServerRouting.h"

WebServerRouting::WebServerRouting()
{
}

WebServerRouting::~WebServerRouting()
{
}

HTTPRequestHandler* WebServerRouting::createRequestHandler(const HTTPServerRequest& request)
{
	if (request.getURI() == "/")
		return new RootController();
	else
		return new RootController();
}

//bool startsWith(string s1, string s2) {
//	int i;
//
//	if (s1.length > s2.length) {
//		for (i = 0; i < s2.length; i++) {
//			if (s1[i] != s2[i]) {
//				return false;
//			}
//		}
//	}
//	else 
//	{
//		for (i = 0; i < s1.length; i++) {
//			if (s1[i] != s2[i]) {
//				return false;
//			}
//		}
//	}
//
//	return true;
//}