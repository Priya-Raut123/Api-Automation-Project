Feature: Test crm api

Scenario Outline: Login
Given Login to crm
| body | <body> |
Examples:
| body                                                 | 
| {"user_name": "karan", "password" : "karan@123"}     |


#| {"user_name": "", "password" : ""}   								 |
#| {"user_name": "karannn", "password" : "karan@123"}   |
#| {"user_name": "karann", "password" : "karanne@123"}  |
#| {"user_name": "karannn", "password" : "karann@123"}  |
#| {"user_name": "nitin", "password" : "10bb6c"}  			 |

Scenario: Product-Type
Given Get the product types

Scenario: source
Given Get the source
Then get sub source
|body | {id: {id: "Credenc Website"}} |

Scenario: Create Lead
Given Create a borrower
|body |	{"firstName":"gyan","email":"gyan@gmail.com","middleName":"kumari","lastName":"malakar","mobile":"8721861454","takeIn":"Spring 2021","abroad":"1","caseSource":"College","caseSubSource":"AAFM","productType":2} |
Then Create a lead 
|body | {"lead":{"firstName":"gyan","email":"gyan@gmail.com","middleName":"kumari","lastName":"malakar","mobile":"8721861454","takeIn":"Spring 2021","abroad":"1","caseSource":"College","caseSubSource":"AAFM","productType":2,"borrowerUuid":"%s"}} |

Scenario: History of Lead
Given History

Scenario: Get Consent of lead
Then Get Consent

Scenario: ask for consent
Then Sent a mail 
|body | {"template_name":"CONSENT_TEMPLATE","purpose":"Customer Mail","template_variable":{"mobile":"8721861454","email_id":"gyan@gmail.com"},"leadId":"%s"} |

Scenario: Upload Documents Details
Then Upload Documents Details

Scenario: Details of a lead
Then Details of Lead

Scenario: Overview Data of lead
Then Overview of Lead

Scenario: City List of India
Then City List of India

Scenario: Complete Fill Data
Then Complete Fill Data

Scenario: save Editing overview Data
Then edit Overview Data
|body | {"borrowerData":{"borrowerUuid":"%s","firstName":"VIJAY","dob":"1970-01-01","middleName":"Sharma","lastName":"RAJAN","email":"vijayrajan9@gmail.com","mobile":"8721861454","altMobile":"9876543218","loanRequired":"","collegePayout":"","currentCountry":"","currentCity":"asdas","currentState":"asdas","currentAddress":"C O CL Rajan Villa 96 Eldeco City IiM Road Near lim Mutakkipur Lucknow Lucknow Uttar Pradesh 226013","collegeName":"WHITEHAT JR","collegeId":"55508","universityCountry":"","courseCategory":"","courseName":"CS","takeIn":"Spring 2021","stream":"","subStream":"","isFldg":"","isExotelRegistered":"no","streamName":"","subStreamName":"","permanentCity":"asda","permanentState":"sadad","experience":"Yes","year":0,"admissionStatus":"","twelthmarks":"2131123","twelthCity":"","twelthschool":"1231","twelthyear":"1231","collagemarks":"","ugcollegeCity":"","degree":"asdaad","collegeyear":"wqed","tenthmarks":"1231312","tenthschool":"12313","upwardsStatus":"not-tagged","productType":"1","productTypeId":"1","productSubType":"","caseSource":"College","caseSubSource":"AAFM","courseStartDate":"","panNo":"ALXPR7005G","courseType":"","courseDuration":6,"lastCurrentCtc":"sdfs","employmentStatusId":"","hlMtInstallment":"","tenor":"","isRepayment":"","course":"","advanceEmi":"","apsadvanceEmi":"","disbursementAmount":"","courseFee":"","utr":"","data":"","disbursementDatetime":"","isDisbursed":"","whjUserName":"","whjUserEmail":"","whjUserMobile":"","leadType":"","partnerIdentifier":"","disbursedId":"","umrn":"","studentFirstName":"","salutation":"MR","maritalStatus":"","aadharNo":"XXXXXXXX9686","new_status":30,"borrowerName":"VIJAY Sharma RAJAN","currentCityState":"asdas, asdas","permanentCityState":"asda,sadad","banking":[{"id":5904,"bothId":"%s","accholdername":"WDWAEA","bankname":"KOTAK","ifsc":"ADSDSADA","acctype":"Savings","branch":"DASDAD","accnumber":"ASDAD","accFor":"","accoperatedsince":"","createdAt":{"date":"2022-01-06 11:16:35.000000","timezone_type":3,"timezone":"Asia/Kolkata"},"modifiedAt":{"date":"2022-01-07 13:04:28.000000","timezone_type":3,"timezone":"Asia/Kolkata"}}],"productTypeText":"Short Ticket"},"cosignerData":[],"testScoreData":[],"parentData":[],"status":{"present_stage":3,"percentage_completed":"55","present_Status":30,"list_of_present_status":[{"id":30,"Name":"Documents Pending","ParentId":"","StageId":3,"DefaultStatus":1,"Percentage":"55"},{"id":31,"Name":"All Documents Collected","ParentId":"","StageId":3,"DefaultStatus":0,"Percentage":"65"},{"id":32,"Name":"Partial Documents Collected","ParentId":"","StageId":3,"DefaultStatus":0,"Percentage":"55"},{"id":33,"Name":"Documents Verified","ParentId":"","StageId":3,"DefaultStatus":0,"Percentage":"75"},{"id":34,"Name":"Consent Pending","ParentId":"","StageId":3,"DefaultStatus":0,"Percentage":"30"},{"id":35,"Name":"Consent Collected","ParentId":"","StageId":3,"DefaultStatus":0,"Percentage":"30"},{"id":37,"Name":"All Checks Verified","ParentId":"","StageId":3,"DefaultStatus":0,"Percentage":"100"},{"id":38,"Name":"Closed","ParentId":"","StageId":3,"DefaultStatus":0,"Percentage":"100"},{"id":42,"Name":"Cosigner Documents","ParentId":"","StageId":3,"DefaultStatus":0,"Percentage":""}],"present_subStatus":"","list_of_present_substatus":[{"id":39,"Name":"Applicant Docs Pending","ParentId":30,"StageId":3,"DefaultStatus":0,"Percentage":"35"},{"id":40,"Name":"Cosigner Docs Pending","ParentId":30,"StageId":3,"DefaultStatus":0,"Percentage":"40"},{"id":41,"Name":"Applicant Consent Pending","ParentId":34,"StageId":3,"DefaultStatus":0,"Percentage":"30"},{"id":134,"Name":"Pending","ParentId":42,"StageId":3,"DefaultStatus":0,"Percentage":"65"},{"id":136,"Name":"Cibil Norms Not Met","ParentId":38,"StageId":3,"DefaultStatus":0,"Percentage":"100"},{"id":138,"Name":"Consent Not Given","ParentId":38,"StageId":3,"DefaultStatus":0,"Percentage":"100"},{"id":139,"Name":"Added","ParentId":42,"StageId":3,"DefaultStatus":0,"Percentage":"70"},{"id":143,"Name":"Cosigner Consent Pending","ParentId":34,"StageId":3,"DefaultStatus":0,"Percentage":"30"}]},"loading":true}|

Scenario: Document Upload
Then upload document





Scenario Outline: credit api test
Given Login to credit using <user_name> and <password>
Examples:
| user_name | password |
| nitin   | 10bb6c |

Scenario: Edit Basic Details 
Then Edit Basic Details 
|body | { "borrowerUuid":"%s", "leadId":"%s", "salutation":"MR", "firstName":"VIJAY", "middleName":"Sharma", "lastName":"RAJAN", "fatherFirstName":"", "fatherMiddleName":"", "fatherLastName":"ashdadadj", "dob":"01-Jan-1970", "gender":"", "maritalStatus":"", "email":"vijayrajan9@gmail.com", "mobile":"sadadadsadsa", "altMobile":"9876543218", "panNo":"ALXPR7005G", "aadharNo":"XXXXXXXX9686", "passportNo":"vgihiowdjij" } | 

Scenario: Edit Borrower Advance
Then Edit Borrower Advance 
|body | { "borrowerUuid": "%s","leadId": "%s","salutation": "MR","firstName": "VIJAY","middleName": "Sharma","lastName": "RAJAN","fatherFirstName": "","fatherMiddleName": "","fatherLastName": "ashdadadj","dob": "01-Jan-1970","gender": "","maritalStatus": "","email": "vijayrajan9@gmail.com","mobile": "sadadadsadsa","altMobile": "9876543218","panNo": "ALXPR7005G","aadharNo": "XXXXXXXX9686","passportNo": "vgihiowdjij"} |

Scenario: Edit Borrowers Address
Then Edit Borrowers Address 
|body | { "borrowerUuid": "%s","leadId":"%s","currentAddress":"C O CL Rajan Villa 96 Eldeco City IiM Road Near lim Mutakkipur Lucknow Lucknow Uttar Pradesh 226013","currentAddressLineTwo":"esfds","currentLandmark":"asddsa","currentCity":"asdas","currentState":"asdas","currentCountry":"","currentPincode":"226013","currentResidenceType":"","yearInCurrentAddress":"sada","permanentAddress":"asda","permanentLandmark":"asdas","permanentCity":"asda","permanentState":"sadad","permanentCountry":"","permanentPincode":"asda" } |

Scenario: Edit Bank Details
Then Edit Bank Details
|body | {    "bothId": "%s",    "accholdername": "WDWAEA",    "bankname": "KOTAK",    "ifsc": "ADSDSADA",    "branch": "DASDAD",    "accnumber": "ASDAD",    "acctype": "Savings",    "leadId": "LEAD-176434" } |

Scenario: Edit Eductaion Details
Then Edit Eductaion Details 
|body | { "borrowerUuid": "%s","leadId": "%s","tenthyear": "2012","tenthschool": "12313","tenthmarks": "1231312","twelthyear": "1231","twelthschool": "1231","twelthmarks": "2131123","collegeyear": "wqed","degree": "asdaad","college": "asdadsa","collagemarks": "","msDegree": "asdsa","mastersyear": "asda","mscollege": "asddasads","mastersmarks": "asdasd"} |

Scenario: Course Edit
Then Course Edit 
|body| {    "borrowerUuid": "%s",    "leadId": "%s",    "admissionStatus": "",    "university": "",    "collegeName": "WHITEHAT JR",    "collegeAddress": "",    "courseName": "no name hai",    "courseCategory": "",    "courseDuration": 6,    "courseType": "",    "courseStream": "",    "courseStartDate": "",    "courseEndDate": "" } |

Scenario: Edit Work Details
Then Edit Work Details
|body | {    "borrowerUuid": "%s",    "leadId": "%s",    "experience": "Yes",    "salaryPaymentModeId": "",    "lastCurrentCtc": "sdfs",    "currentEmploymentTenure": 0,    "year": "0",    "workEmailId": "sdfds",    "professionTypeId": "",    "lastCurrentEmployer": "DSFDSF",    "jobRole": "SDFDSF",    "companyLocation": "dsfdsfds" } |

Scenario: Upload Multiple Documents
Then Upload Multiple Documents 






#Examples:
#| x-auth-id-1 | x-auth-token-1                   | x-auth-id-2 |  x-auth-token-2                | x-auth-id-3 |  x-auth-token-3                |
#| 458         | b4a5ccf68fd33275746496ed8a9f1976 | 393         |3bcd7c322f8c680bf6759a2908084fc0| 458         |6b1cabc4538013bb92b82961d477eeb8|
