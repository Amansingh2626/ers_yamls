(self.webpackChunk_unified_account=self.webpackChunk_unified_account||[]).push([[2368],{42368:(e,t,n)=>{"use strict";n.d(t,{Z:()=>p});var a=n(74315),o=n(55238),i=n(2411),c=n(77277),l=n(38592),r=n(48049),d=n(29609),T=n(64874),u=n(60552),s=n(41744),m=n.n(s),C=n(79692),A=n(12420),N=n(27490),E=n(44104),h=n.n(E),f=(0,C.Z)((function(e){return(0,A.Z)({dialogWrapper:{textAlign:"center","& .MuiDialog-container":{width:"100%"},"& .MuiDialogContent-root":{marginTop:e.spacing(2),marginBottom:e.spacing(3),marginRight:"auto",marginLeft:"auto"}},fontHeavy:{fontWeight:e.typography.fontWeightBold},modalContentSpacing:{marginBottom:e.spacing(3)},btnSpace:{marginRight:e.spacing(2)}})}));const p=function(e){var t=e.accountTypes,n=e.loading,s=e.actionButtons,C=e.selectionModel,A=(e.onSelectionModelChange,e.onSelectReseller),E=((0,o.Z)(e,["accountTypes","loading","actionButtons","selectionModel","onSelectionModelChange","onSelectReseller"]),(0,d.useTranslation)().t),p=(f(),h()().checkPermission),S=i.useState([]),_=(0,a.Z)(S,2),g=_[0],U=_[1],O=i.useCallback((function(){return U(C)}),[]);i.useEffect((function(){O()}),[O]);var I=[{field:"accountTypeId",headerName:E("accountTypeId"),sortable:!0,width:200},{field:"description",headerName:E("description"),sortable:!1,width:200},{field:"minAccountBalance",headerName:E("minAccountBalance"),sortable:!1,width:180},{field:"maxAccountBalance",headerName:E("maxAccountBalance"),sortable:!1,width:180},{field:"minTransactionAmount",headerName:E("minTransactionAmount"),sortable:!1,width:180},{field:"maxTransactionAmount",headerName:E("maxTransactionAmount"),sortable:!1,width:180},{field:"creditLimit",headerName:E("creditLimit"),sortable:!1,width:180},{field:"countLimit",headerName:E("countLimit"),sortable:!1,width:180},{field:"payLimit",headerName:E("payLimit"),sortable:!1,width:180},{field:"expiryDate",headerName:E("expiryDate"),sortable:!1,width:200,type:"dateTime"},{field:"url",headerName:E("URL"),sortable:!1,width:350}];return s&&I.push({field:"actions",headerName:E("actions"),sortable:!1,width:200,renderCell:function(e){return i.createElement("div",null,i.createElement(l.Z,{size:"small",color:"primary",variant:"text","aria-label":"account types action buttons"},i.createElement(c.Z,{id:"view-account-type-button-".concat(e.row.id),component:r.Link,to:"/home/account/account-types/view/"+e.getValue("id")},i.createElement(u.Z,{fontSize:"small"})),p(N.R.EDIT_ACCOUNTTYPE_BUTTON)&&i.createElement(c.Z,{id:"edit-account-type-button-".concat(e.row.id),component:r.Link,to:"/home/account/account-types/edit/"+e.getValue("id")},i.createElement(T.Z,{fontSize:"small"}))))},flex:200}),i.createElement(m(),{onSelectionModelChange:function(e){U(e.selectionModel)},selectionModel:g,checkboxSelection:!0,loading:n,columnBuffer:4,rows:t,onRowSelected:function(e){A&&A(e.data)},getRowId:function(e){return e.accountTypeId},columns:I})}},27490:(e,t,n)=>{"use strict";n.d(t,{R:()=>a});var a={CREATE_ACCOUNTTYPE:"CREATE_ACCOUNTTYPE",VIEW_ACCOUNTTYPE:"VIEW_ACCOUNTTYPE",EDIT_ACCOUNTTYPE:"EDIT_ACCOUNTTYPE",ACCOUNTTYPES_LISTING:"ACCOUNTTYPES_LISTING",EDIT_ACCOUNTTYPE_BUTTON:"EDIT_ACCOUNTTYPE_BUTTON",SEARCH_TRANSACTIONS:"SEARCH_TRANSACTIONS",CREATE_ACCOUNT:"CREATE_ACCOUNT",VIEW_ACCOUNT:"VIEW_ACCOUNT",SEARCH_ACCOUNTS:"SEARCH_ACCOUNTS",ACCOUNTS_LISTING:"ACCOUNTS_LISTING",MANUAL_ADJUSTMENT:"MANUAL_ADJUSTMENT"}}}]);