Feature:
  Scenario: Fetch google.com
	Given url 'https://www.google.com'
	When method GET
	Then status 200
