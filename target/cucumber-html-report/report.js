$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("features/smoke.feature");
formatter.feature({
  "line": 1,
  "name": "Test Store",
  "description": "I want to test store",
  "id": "test-store",
  "keyword": "Feature"
});
formatter.before({
  "duration": 693738881,
  "status": "passed"
});
formatter.scenario({
  "line": 4,
  "name": "Test Store Landing Page",
  "description": "",
  "id": "test-store;test-store-landing-page",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 5,
  "name": "I have Store url",
  "keyword": "Given "
});
formatter.match({
  "location": "AllCucumberSteps.i_have_Store_url()"
});
