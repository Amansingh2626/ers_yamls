(self.webpackChunk_unified_account=self.webpackChunk_unified_account||[]).push([[2849],{22998:(n,t,c)=>{"use strict";c.d(t,{nA:()=>a,_H:()=>u,Jj:()=>o,o1:()=>r,T8:()=>s});var e=c(84496),a=function(n){return(0,e.Z)("/ams/v1/management/searchAccounts",{method:"POST",body:n})},u=function(n){return(0,e.Z)("/ams/v1/management/accounts/"+n)},o=function(n){return(0,e.Z)("/ams/v1/management/searchAccountTransactions",{method:"POST",body:n})},r=function(n){return n.account.activationDate=n.account.activationDate+" 00:00:00",(0,e.Z)("/ams/v1/management/accounts",{method:"POST",body:[n]})},s=function(n,t){return(0,e.Z)("/ams/v1/management/accounts/".concat(n,"/").concat(t))}},79349:(n,t,c)=>{"use strict";c.r(t),c.d(t,{default:()=>r});var e=c(74315),a=c(2411),u=c(38970),o=c(23664);const r=function(n){var t=n.id,c=n.children,r=a.useState([]),s=(0,e.Z)(r,2),i=s[0],m=s[1],f=a.useState(!1),d=(0,e.Z)(f,2),h=d[0],l=d[1],v=(0,o.Z)().showErrorMessage;return a.useEffect((function(){l(!0),(0,u.lx)(t).then((function(n){m(n.accountTypeList)})).finally((function(){return l(!1)})).catch((function(n){return v(n)}))}),[t]),a.createElement(a.Fragment,null,c({accountTypes:i,loading:h}))}}}]);