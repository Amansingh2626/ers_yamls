(self.webpackChunk_unified_transaction=self.webpackChunk_unified_transaction||[]).push([[4479],{54328:(e,t,a)=>{"use strict";a.d(t,{Z:()=>i});var n=a(2411),A=a(19653),r=a(29609),T=a(41744),l=a.n(T);const i=function(e){var t=e.accounts,a=e.loading,T=(e.type,(0,r.useTranslation)().t),i=[{field:"accountId",headerName:T("accountId"),width:150,filterable:!1,sortable:!1},{field:"accountTypeId",headerName:T("accountTypeId"),width:160,filterable:!1,sortable:!1},{field:"amount",headerName:T("amount"),width:150,filterable:!1,sortable:!1},{field:"balanceBefore",headerName:T("balanceBefore"),width:150,filterable:!1,sortable:!1,valueGetter:function(e){return e.row.balanceBefore.includes("undefined")?"-":e.row.balanceBefore}},{field:"balanceAfter",headerName:T("balanceAfter"),width:150,filterable:!1,sortable:!1,valueGetter:function(e){return e.row.balanceAfter.includes("undefined")?"-":e.row.balanceAfter}},{field:"comment",headerName:T("comment"),width:150,filterable:!1,sortable:!1},{field:"tag",headerName:T("tag"),width:140,filterable:!1,sortable:!1}],C={Toolbar:function(){return n.createElement(A.GridToolbarContainer,null)}};return n.createElement(l(),{autoHeight:!0,components:C,rows:t.sort((function(e,t){return e.marginRuleId-t.marginRuleId})),loading:a,columns:i,getRowId:function(e){return null==e?void 0:e.id},columnBuffer:3,disableMultipleColumnsSorting:!0,disableColumnFilter:!0,hideFooterPagination:!0})}},11312:(e,t,a)=>{"use strict";a.d(t,{Z:()=>N});var n=a(7560),A=a(2411),r=a(29609),T=a(60905),l=a(86689),i=a(90436),C=a(79692),o=a(12420),R=a(48049),E=(0,C.Z)((function(e){return(0,o.Z)({fieldHeading:{fontWeight:"bold",textTransform:"capitalize",fontSize:14},fieldValue:{textTransform:"capitalize",fontSize:14}})}));const N=function(e){var t=e.headingProps,a=void 0===t?{xs:12,sm:3}:t,C=e.valueProps,o=void 0===C?{xs:12,sm:3}:C,N=e.title,_=e.value,c=e.isLink,d=void 0!==c&&c,u=e.LinkRoute,I=void 0===u?"":u,s=((0,r.useTranslation)().t,E()),f=(0,R.useHistory)();return A.createElement(A.Fragment,null,A.createElement(T.Z,(0,n.Z)({item:!0},a),A.createElement(i.Z,{className:s.fieldHeading},N)),A.createElement(T.Z,(0,n.Z)({item:!0},o),d?A.createElement(l.Z,{component:"button",className:s.fieldValue,variant:"body2",onClick:function(){f.push("".concat(I,"/").concat(_))}},_):A.createElement(i.Z,{className:s.fieldValue},_)))}},27490:(e,t,a)=>{"use strict";a.d(t,{R:()=>n});var n={TOPUP:"TOPUP",BUNDLE:"CREATE_BUNDLE",AIRTIMESTOCK:"AIRTIMESTOCK",AIRTIME_POSTPAID:"AIRTIME_POSTPAID",SEARCH:"SEARCH",VIEW_TRANSACTION:"SEARCH_ARCHIVED_TRANSACTION",REVERSE_TRANSACTION:"TRANSATION_REVERSAL",O2C:"O2C",CREATE_O2C:"CREATE_O2C",O2C_WITHDRAWAL:"O2C_WITHDRAWAL",O2C_APPROVAL_LIST:"O2C_APPROVAL_LIST",C2C:"C2C",P2P:"P2P",P2P_PIN_RESET:"P2P_PIN_RESET",CREATE_C2C:"CREATE_C2C",C2C_WITHDRAWAL:"C2C_WITHDRAWAL",C2C_APPROVAL_LIST:"C2C_APPROVAL_LIST",APPROVE_C2C_TRANSACTION:"APPROVE_C2C_TRANSACTION",REJECT_C2C_TRANSACTION:"REJECT_C2C_TRANSACTION",PENDING_TRANSACTIONS:"PENDING_TRANSACTIONS",APPROVE_TRANSACTION:"APPROVE_TRANSACTION",REJECT_TRANSACTION:"CANCEL_TRANSACTION",RETURN:"RETURN",CREATE_RETURN:"CREATE_RETURN",HIERARCHY_SEARCH:"SEARCH_HIERARCHY",APPROVE_O2C_TRANSACTION:"APPROVE_O2C_TRANSACTION",REJECT_O2C_TRANSACTION:"REJECT_O2C_TRANSACTION",ACTIVATE_BUNDLE:"ACTIVATE_BUNDLE",MANUAL_ADJUSTMENT:"MANUAL_ADJUSTMENT",STANDARD_BUNDLE_LIST:"STANDARD_BUNDLE_LIST"}}}]);