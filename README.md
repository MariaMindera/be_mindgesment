# BE_MindGesment

# EndPoints Controller  
  
## Constants  
GET: `/constants/categories`    
RETURN:  `{ "income": value, "expense": value }` 

<hr/>  

GET:  `/constants/coins`  
RETURN: Array das coins  

<hr/>  

GET: `/constants/transactionTypes`  
RETURN: Array das Transaction Types  
  
## Transaction  
- Add Transaction  
  
	POST: `/transaction`  
	BODY = `{  title, amount, date, category, type, ? description } `
	RETURN: `201_CREATED { title, amount, date, category, type, description }  `

<hr/>    

- Edit Transaction  
    PUT: `/transaction/{transaction_id}`  
	BODY = `{ title, amount, date, category, type, ? description }`
	RETURN: `{ title, amount, date, category, type, description }`
      
<hr/>  
  
- Delete
  DELETE: `/transaction/{transaction_id}`

<hr/>  

- Find All
  GET: `/transaction`
  PARAM = `page (int >= 0)`
  RETURN: `{ list, total }` Array de 10 transactions
  
<hr/>  

- Find All By Category
  GET: `/transaction/category`
  PARAM = `category, page (int >= 0)`
  RETURN: `HEADER: X-TransactionType-Total` Array de 10 transactions
  
<hr/>  

- Find All By Type
  GET: `/transaction/type`
  PARAM = `type, page (int >= 0)`
  RETURN: `HEADER: X-TransactionType-Total` Array de 10 transactions
  
<hr/>  

- Find All By Date
  GET: `/transaction/date`
  PARAM = `date, page (int >= 0)`
  RETURN: `HEADER: X-TransactionType-Total` Array de 10 transactions

## User
- Change Email
  PATCH: `/user/email`
  PARAM = `email`
  RETURN: `{ id, email, username, coin, earnings, expenses, monthlyPeriod }`

<hr/>

- Change Username
  PATCH: `/user/username`
  PARAM = `username`
  RETURN: `{ id, email, username, coin, earnings, expenses, monthlyPeriod }`

<hr/>

- Change Coin
  PATCH: `/user/coin`
  PARAM = `coin`
  RETURN: `{ id, email, username, coin, earnings, expenses, monthlyPeriod }`

<hr/>

- Change Earnings
  PATCH: `/user/earnings`
  PARAM = `earnings (double >= 0.00)`
  RETURN: `{ id, email, username, coin, earnings, expenses, monthlyPeriod }`

<hr/>

- Change Expenses
  PATCH: `/user/expenses`
  PARAM = `expenses (double >= 0.00)`
  RETURN: `{ id, email, username, coin, earnings, expenses, monthlyPeriod }`

<hr/>

- Change Monthly Period
  PATCH: `/user/monthlyPeriod`
  PARAM = `monthlyPeriod (1 <= int <= 31)`
  RETURN: `{ id, email, username, coin, earnings, expenses, monthlyPeriod }`

<hr/>

- Get User
  GET: `/user`
  RETURN: `{ id, email, username, coin, earnings, expenses, monthlyPeriod, role }`

<hr/>

- Get Balance User
  GET: `/user/balance`
  RETURN: `{ income, expense }`

<hr/>

- Get All Balance User
  GET: `/user/balance/all`
  RETURN: `{ income, expense }`

<hr/>

- Register User
  POST: `/user/register`
  BODY = `{ email, password, username, coin, ?earnings, expenses }`
  RETURN:  `201_CREATED`

<hr/>

- Forgot Password
  POST: `/forgot-password`
  BODY = `{ email }`

<hr/>

- Request Change Password
  GET: `/change-password`

<hr/>

- Change Password
  POST: `/change-password`
  BODY = `{ password, token }`  (token vai no url) 

<hr/>

- Request Change Email
  GET: `/change-email`

<hr/>

- Change Email
  POST: `/change-email`
  BODY = `{ email, token }`  (token vai no url) 
  
## Wish
- Get Balance
  GET: `/wish/balance`
  RETURN: `{ balance }`

<hr/>

- Get Proposal Amount
  GET: `/wish/proposalAmount`
  RETURN:  `{proposalAmount}`

<hr/>

- Get Transactions of a wish
  GET: `/wish/transaction/{wish_id}`
  PARAM = `page (int >= 0)`
  RETURN:  `HEADER: X-TransactionType-Total` Array 10 Transactions

<hr />

- Add Balance to Wish
  POST: `/wish/balance/{wish_id}`
  PARAM = `balance (long positive)`

<hr/>

- Complete Wish
  POST: `/wish/complete/{wish_id}`

<hr/>

- Edit
  PUT: `/wish/{wish_id}`
  BODY = `{ name, total, proposalAmount, date, description }`
  RETURN: `{ name, total, proposalAmount, balance, date, description }`

<hr/>

- Delete
  DELETE: `/wish/{wish_id}`

<hr/>

- Find All
  GET: `/wish`
  PARAM = `page (int >= 0)`
  RETURN: `{ list:Array de 10 wishes, completed, total  }`

### First Wish
- Get Proposal Amount
  GET: `/wish/first/proposalAmount`
  PARAM = `totalAmount (int >= 0)`
  RETURN: `{ proposalAmount, date }`

<hr/>

- Add First Wish
  POST: `/wish/first`
  BODY = `{ name, total, proposalAmount, ?description }`
  RETURN: `201_CREATED { name, total, proposalAmount, balance, date, description }`

### Others Wishes
- Get Proposal Amount Others
  GET: `/wish/others/proposalAmount`
  RETURN: `{ proposalAmount, list }` Lista de todas as wishes do user

<hr/>

- Add Others Wishes
  POST: `/wish/others`
  BODY = `{ wish, othersWishes }` Wish é a nova wish, e as othersWishes é um array com a modificação.
  RETURN: `201_CREATED`

## Tips
- Add Tip
  POST: `/admin/tips`
  Body = `{ title, description, imageUrl }`
  RETURN: `201_CREATED` 

<hr/>

- Edit Tip
  PUT: `/admin/tips/{tip_id}`
  Body = `{ title, description, imageUrl }`
  RETURN: `{ title, description, imageUrl }` 

<hr/>

- Delete Tip
  DELETE: `/admin/tips/{tip_id}`
  RETURN: `200_OK`

<hr/>

- Find All
  GET: `/tips`
  PARAM = `page (int >= 0)`
  RETURN: `{ list:Array de 10 tip, total }`