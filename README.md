###Moteefe code challenge

This project was made to answer the moteefe code challenge. It is built as a Spring Boot App, with one rest endpoint that 
will create an order with the structure requested in the challenge. 

To develop this project the IDE used was the Spring Tool Suit 4.6.0, and the app was ran from the IDE itself, running 
it from the Boot dashboard. The endpoint is a POST request, and to call it when we run it in our machine, in the IDE:

---

 localhost:8080/v1/order
 
---

In the body of this request we give the parameters that were determined in a json format. Bellow an example of this json 
holding the parameters: 

---
 
{
	"region":"us", 
	"items":[
		{
			"title": "black_mug", 
			"count": 4
		}, 
		{
			"title": "white_mug", 
			"count": 1
		}
		]
}

---

In this project there were some tests made using Junit5, that can be run using, also in the IDE, Run as -> Junit test.
