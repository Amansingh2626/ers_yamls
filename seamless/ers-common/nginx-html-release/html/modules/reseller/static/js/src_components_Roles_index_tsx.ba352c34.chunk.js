(self.webpackChunk_unified_reseller=self.webpackChunk_unified_reseller||[]).push([["src_components_Roles_index_tsx"],{7560:(e,t,n)=>{"use strict";function r(){return(r=Object.assign||function(e){for(var t=1;t<arguments.length;t++){var n=arguments[t];for(var r in n)Object.prototype.hasOwnProperty.call(n,r)&&(e[r]=n[r])}return e}).apply(this,arguments)}n.d(t,{Z:()=>r})},55238:(e,t,n)=>{"use strict";function r(e,t){if(null==e)return{};var n,r,o=function(e,t){if(null==e)return{};var n,r,o={},a=Object.keys(e);for(r=0;r<a.length;r++)n=a[r],t.indexOf(n)>=0||(o[n]=e[n]);return o}(e,t);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(e);for(r=0;r<a.length;r++)n=a[r],t.indexOf(n)>=0||Object.prototype.propertyIsEnumerable.call(e,n)&&(o[n]=e[n])}return o}n.d(t,{Z:()=>r})},84496:(e,t,n)=>{"use strict";n.d(t,{Z:()=>o});var r=n(61416);const o=n.n(r)()},58752:(e,t,n)=>{"use strict";n.d(t,{K:()=>u});var r,o,a,l=n(84496),s=window.config,c=null!==(r=null==s?void 0:s.showActiveContracts)&&void 0!==r&&r,i=null!==(o=null==s||null===(a=s.resellerAdditionalConfig)||void 0===a?void 0:a.activeContractLimit)&&void 0!==o?o:0,u=function(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:"",t=(e+=c?";contractStatus=1":"").includes("?")?"&":"?",n=i>0&&!e.includes("size=")?"".concat(t,"size=").concat(i):"";return e+=n,(0,l.Z)("/acms/v2/contracts".concat(e))}},24157:(e,t,n)=>{"use strict";n.d(t,{T:()=>o});var r=n(84496),o=function(e){return(0,r.Z)("/groupmanagementsystem/v1/group/member/findGroups?userId=".concat(e))}},96545:(e,t,n)=>{"use strict";n.d(t,{fZ:()=>r.fZ,sC:()=>r.sC,CQ:()=>r.CQ,hm:()=>r.hm,c1:()=>r.c1,pN:()=>r.pN,wv:()=>r.wv,OT:()=>r.OT,it:()=>r.it,eW:()=>r.eW,Fg:()=>r.Fg,Nk:()=>r.Nk,qu:()=>r.qu,Nb:()=>r.Nb,kS:()=>r.kS,GX:()=>r.GX,iA:()=>r.iA,OV:()=>r.OV,gH:()=>r.gH,E5:()=>r.E5,wm:()=>r.wm,TF:()=>r.TF,Ah:()=>r.Ah,HB:()=>r.HB,XD:()=>r.XD,FF:()=>r.FF,lX:()=>r.lX,Gh:()=>r.Gh,NF:()=>r.NF,cG:()=>o.cG,ZV:()=>o.ZV,cY:()=>o.cY,F3:()=>o.F3,b7:()=>o.b7,QX:()=>a.QX,lg:()=>a.lg,uP:()=>a.uP,G0:()=>a.G0,Iq:()=>a.Iq,ec:()=>l.ec,p9:()=>l.p9,ob:()=>l.ob,Kf:()=>s.K,t9:()=>c.t9,Sb:()=>c.Sb,wy:()=>c.wy,sQ:()=>c.ZV,g4:()=>c.g4,j8:()=>c.j8,WY:()=>c.WY,TJ:()=>i.T});var r=n(14674),o=n(31718),a=n(1111),l=n(95191),s=n(58752),c=n(35005),i=n(24157)},95191:(e,t,n)=>{"use strict";n.d(t,{ec:()=>o,ob:()=>a,Ww:()=>l,l4:()=>s,p9:()=>c});var r=n(84496),o=function(e){return(0,r.Z)("/dms/v1/password/policies",{method:"POST",body:e})},a=function(){return(0,r.Z)("/dms/v1/password/policies")},l=function(e){return(0,r.Z)("/dms/v1/password/policies/".concat(e))},s=function(e,t){return(0,r.Z)("/dms/v1/password/policies/".concat(e),{method:"PUT",body:t})},c=function(e){return(0,r.Z)("/dms/v1/password/policies/".concat(e),{method:"DELETE"})}},1111:(e,t,n)=>{"use strict";n.d(t,{fQ:()=>o,lg:()=>a,Iq:()=>l,uP:()=>s,G0:()=>c,Np:()=>i,QX:()=>u});var r=n(84496),o=function(e){return(0,r.Z)("/dms/v1/resellers/types/create",{method:"POST",body:e})},a=function(){return(0,r.Z)("/dms/v1/resellers/types/all")},l=function(e){return(0,r.Z)("/dms/v1/resellers/types/".concat(e,"/parents"))},s=function(e){return(0,r.Z)("/dms/v1/resellers/types/".concat(e,"/children"))},c=function(e){return(0,r.Z)("/dms/v1/resellers/types/".concat(e))},i=function(e){return(0,r.Z)("/dms/v1/resellers/types/update",{method:"PUT",body:e})},u=function(e){return(0,r.Z)("/dms/v1/resellers/types/".concat(e),{method:"DELETE"})}},14674:(e,t,n)=>{"use strict";n.d(t,{Nk:()=>i,Fg:()=>u,OV:()=>d,FF:()=>m,iA:()=>f,eW:()=>p,gH:()=>h,lX:()=>v,pN:()=>g,wv:()=>b,hm:()=>y,kS:()=>Z,uZ:()=>w,c0:()=>T,c1:()=>S,CQ:()=>E,E5:()=>P,NF:()=>I,qu:()=>O,fB:()=>x,OT:()=>R,HB:()=>D,XD:()=>F,wm:()=>U,it:()=>A,Gh:()=>B,sC:()=>_,fZ:()=>q,Ah:()=>G,v$:()=>L,GX:()=>W,Nb:()=>j,TF:()=>X});var r,o,a=n(84496),l=window.config||{},s=(null==l?void 0:l.defaultPerPageItems)||20,c=null!==(r=null==l||null===(o=l.resellerAdditionalConfig)||void 0===o?void 0:o.callIsBasicResellerInfoAPI)&&void 0!==r&&r,i=function(){return(0,a.Z)("/groupmanagementsystem/v1/group?status=active")},u=function(e){return(0,a.Z)("/groupmanagementsystem/v1/group/name/".concat(e))},d=function(e){var t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:0,n=arguments.length>2?arguments[2]:void 0,r=n||s,o=t||n?"?limit=".concat(r,"&offset=").concat(t):"";return(0,a.Z)("/dms/v1/resellers/resellerChildren/".concat(e).concat(o))},m=function(e,t,n){var r=arguments.length>3&&void 0!==arguments[3]?arguments[3]:"",o=c?"searchResellerBasicInfoByAttribute":"searchResellersByAttribute";return r&&(o=r),(0,a.Z)("/dms/auth/".concat(o,"?limit=").concat(t,"&offSet=").concat(n),{method:"POST",body:e})},f=function(e){return(0,a.Z)("/dms/v1/resellers/types/".concat(e,"/parents"))},p=function(e,t,n){var r=n?"allowedTypes":t?"descendants":"children";return(0,a.Z)("/dms/v1/resellers/types/".concat(e,"/").concat(r))},h=function(e){return(0,a.Z)("/dms/auth/getResellerInfo",{method:"POST",body:e})},v=function(e){return(0,a.Z)("/dms/auth/updateReseller",{method:"POST",body:e})},g=function(e){return(0,a.Z)("/dms/auth/changeResellerType",{method:"POST",body:e})},b=function(e){return(0,a.Z)("/dms/auth/v1/resellerChangeState",{method:"POST",body:e})},y=function(e,t,n){return(0,a.Z)("/dms/v1/lifecycle/dealer/status/".concat(n,"/").concat(t),{method:"POST",body:e})},Z=function(){return(0,a.Z)("/lifecycle/reseller/status/fetch/labels")},w=function(e){return(0,a.Z)("/dms/auth/expirePassword",{method:"POST",body:e})},T=function(e){return(0,a.Z)("/dms/auth/forgetPassword",{method:"POST",body:e})},S=function(e){return(0,a.Z)("/dms/auth/changeParent?newResellerParentId=".concat(e.newResellerParentId,"&resellerId=").concat(e.resellerId),{method:"POST",body:e})},E=function(e){return(0,a.Z)("/dms/auth/generateId",{method:"POST",body:e})},P=function(e){var t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:"DMS";return(0,a.Z)("/template/v1/component/".concat(t,"/type/").concat(e))},I=function(e){return(0,a.Z)("/iprs/iprs-complete-check",{method:"POST",body:e})},O=function(e){return(0,a.Z)(e)},N=0,k=null,x=function(e){var t,n,r=new Headers;r.append("authorization",localStorage.getItem("token")||""),r.append("Accept-Language","en-US;en-IN;q=0.9,en-US;q=0.7,en-GB;q=0.6");var o={method:"POST",headers:r,body:e,redirect:"follow"},a="".concat(window.config.protocol,"://").concat(window.config.server,":").concat(window.config.port).concat("/api/dms/auth/addReseller"),l=null===(t=window)||void 0===t||null===(n=t.config)||void 0===n?void 0:n.isAllowedTokenRefresh;return fetch(a,o).then((function(t){var n=t.headers.get("authorization");return n?localStorage.setItem("token",n):l&&N<=0&&localStorage.getItem("token")&&(k=e,C().then((function(e){N=0,k&&x(k).then((function(e){0===e.resultCode&&window.history.back()}))})).catch((function(e){++N}))),t.json()}))},C=function(){var e="".concat(window.config.protocol,"://").concat(window.config.server,":").concat(window.config.port).concat(window.config.service,"/refreshToken");return(0,a.Z)(e,{method:"POST"})},R=function(e){return(0,a.Z)("/dms/v1/customers/?nationalId=".concat(e))},D=function(e){return(0,a.Z)("/lifecycle/reseller/".concat(e,"/transactions"))},F=function(e){return(0,a.Z)("/dms/v1/resellers/updatePayLimit",{method:"PUT",body:e})},U=function(e){return(0,a.Z)("/dms/auth/userAlreadyExists",{method:"POST",body:e})},A=function(e){return(0,a.Z)("/dms/auth/deleteResellerUsers",{method:"DELETE",body:e})},B=function(e){return(0,a.Z)("/dms/auth/updateResellerUsers",{method:"PUT",body:e})},_=function(e){return(0,a.Z)("/dms/auth/addResellerUsers",{method:"POST",body:e})},q=function(e,t){return(0,a.Z)("/groupmanagementsystem/v1/group/".concat(e,"/member"),{method:"POST",body:t})},G=function(e,t){return(0,a.Z)("/groupmanagementsystem/v1/group/".concat(e,"/member?memberIds=").concat(t),{method:"DELETE"})},L=function(e,t,n){return(0,a.Z)("/dms/auth/bulkSearchResellersByAttribute?limit=".concat(t,"&offSet=").concat(n),{method:"POST",body:e})},W=function(){return(0,a.Z)("/nms/api/messageTemplateType/CUSTOM/messages?page=0&size=".concat(s,"&sort=id%3Aasc"))},j=function(){return(0,a.Z)("/scc/v1/frequencies")},X=function(e,t){return(0,a.Z)("/txe/reseller/".concat(e,"SubReseller"),{method:"POST",body:t})}},31718:(e,t,n)=>{"use strict";n.d(t,{cG:()=>o,F3:()=>a,cY:()=>l,b7:()=>s,ZV:()=>c});var r=n(84496),o=function(e){return(0,r.Z)("/dms/v1/roles/5",{method:"POST",body:e})},a=function(){return(0,r.Z)("/dms/v1/roles/")},l=function(e){return(0,r.Z)("/dms/v1/roles/".concat(5,"/").concat(e))},s=function(e,t){return(0,r.Z)("/dms/v1/roles/".concat(5,"/").concat(e),{method:"PUT",body:t})},c=function(e){return(0,r.Z)("/dms/v1/roles/".concat(5,"/").concat(e),{method:"DELETE"})}},35005:(e,t,n)=>{"use strict";n.d(t,{j8:()=>o,g4:()=>a,wy:()=>l,t9:()=>s,WY:()=>c,ZV:()=>i,Sb:()=>u});var r=n(84496),o=function(){return(0,r.Z)("/dms/v1/template/dropdown")},a=function(e){return(0,r.Z)("/dms/v1/template/dropdown/".concat(e))},l=function(){return(0,r.Z)("/dms/v1/template/dropdown/fields/all")},s=function(e){return(0,r.Z)("/dms/v1/template/dropdown",{method:"POST",body:e})},c=function(e,t){return(0,r.Z)("/dms/v1/template/dropdown/".concat(t),{method:"PUT",body:e})},i=function(e){return(0,r.Z)("/dms/v1/template/dropdown/byId/".concat(e),{method:"GET"})},u=function(e){return(0,r.Z)("/dms/v1/template/dropdown/".concat(e),{method:"DELETE"})}},24160:(e,t,n)=>{"use strict";n.r(t),n.d(t,{default:()=>g});var r=n(7560),o=n(2411),a=n(9699),l=n(79692),s=n(12420),c=n(50835),i=n(70773),u=n(33553),d=n(6277),m=n(36285),f=n(29609),p=n(50921),h=n(23664),v=(0,l.Z)((function(e){return(0,s.Z)({form:{display:"flex",flexDirection:"column",flexWrap:"wrap"},element:{margin:e.spacing(1),marginBottom:e.spacing(3),minWidth:275},input:{},description:{"& .MuiInput-underline:before":{width:"calc(100% - 10px) !important"}},button:{}})}));const g=function(e){var t=e.data,n=e.onFormSubmit,l=e.edit,s=e.view,g=e.hideFields,b=v(),y=(0,f.useTranslation)().t,Z=(0,h.Z)().showErrorMessage,w=a.Ry().shape({addressLock:a.Z_(),roleName:a.Z_().required(y("__isRequiredField",{fieldName:y("roleName")})),importId:a.Z_().required(y("__isRequiredField",{fieldName:y("importId")})),parentRoleKey:a.Rx(),passwordPolicyKey:a.Rx(),roleDescription:a.Z_(),terminalUser:a.O7(),userIdRegexp:a.Z_(),webUser:a.O7()}),T=(0,c.TA)({initialValues:t,validationSchema:w,onSubmit:function(e){return n&&n(e,(function(t){t||O(e)}))},enableReinitialize:!0}),S=T.values,E=T.handleChange,P=T.setFieldValue,I=T.handleSubmit,O=T.handleReset,N=T.errors,k=T.validateForm;return o.createElement(u.Z,{display:"flex",flexDirection:"column"},o.createElement(u.Z,{display:"flex",flexDirection:"row",alignItems:"center"},o.createElement(i.SeamlessInput,(0,r.Z)({required:!0,error:!!N.roleName},null!=g&&g.includes("passwordPolicyKey")?null:{fullWidth:!0},{className:(0,d.default)(b.element,b.input),name:"roleName",value:S.roleName,onBlur:function(){if(S.roleName){var e=S.roleName.split(" ").join("-");e&&P("importId",e)}},onChange:E,label:y("roleName"),helperText:(null==N?void 0:N.roleName)||y("pleaseEnter_",{fieldName:y("roleName")}),disabled:s})),!(null!=g&&g.includes("passwordPolicyKey"))&&o.createElement(p.Z,{name:"passwordPolicyKey",disabled:s,value:S.passwordPolicyKey,handleChange:E}),!(null!=g&&g.includes("importId"))&&o.createElement(i.SeamlessInput,{error:!!N.importId,className:(0,d.default)(b.element,b.input),name:"importId",fullWidth:!0,required:!0,value:S.importId,helperText:(null==N?void 0:N.importId)||y("pleaseEnter_",{fieldName:y("importId")}),onChange:E,label:y("importId"),disabled:s||l})),o.createElement(u.Z,{display:"flex",flexDirection:"row",alignItems:"center"},!(null!=g&&g.includes("addressLock"))&&o.createElement(i.SeamlessInput,{error:!!N.addressLock,className:(0,d.default)(b.element,b.input),name:"addressLock",value:S.addressLock,onChange:E,label:"Address Lock",helperText:"Enter address lock",disabled:s}),!(null!=g&&g.includes("userIdRegexp"))&&o.createElement(i.SeamlessInput,{error:!!N.userIdRegexp,className:(0,d.default)(b.element,b.input),name:"userIdRegexp",value:S.userIdRegexp,onChange:E,label:"Userid regexp",helperText:"Enter userid regexp",disabled:s})),o.createElement(u.Z,{display:"flex",flexDirection:"row",alignItems:"center"},o.createElement(i.SeamlessInput,{error:!!N.roleDescription,className:(0,d.default)(b.element,b.description),name:"roleDescription",value:S.roleDescription,onChange:E,label:"Description",helperText:"Enter role description",disabled:s,multiline:!0,rows:4,rowsMax:4,fullWidth:!0})),!(null!=g&&g.includes("webUser"))&&o.createElement(u.Z,{display:"flex",flexDirection:"row",alignItems:"center"},o.createElement(m.Z,{className:(0,d.default)(b.element),name:"webUser",checked:Boolean(S.webUser),label:"Web user (will permit user to log-in on website i.e. ERSAdmin or ResellerAdmin depending on application)",handleChange:function(e){return E({target:{value:e.target.checked,name:"webUser"}})},color:"primary",disabled:s||!1})),!(null!=g&&g.includes("terminalUser"))&&o.createElement(u.Z,{display:"flex",flexDirection:"row",alignItems:"center"},o.createElement(m.Z,{className:(0,d.default)(b.element),name:"terminalUser",checked:Boolean(S.terminalUser),label:"Terminal user (link terminal specific settings to users with this role i.e. terminal user type, card number)",handleChange:function(e){return E({target:{value:e.target.checked,name:"terminalUser"}})},color:"primary",disabled:s||!1})),!s&&o.createElement(u.Z,null,o.createElement(i.SeamlessButton,{onClick:function(e){k().then((function(e){var t=Object.values(e);t.length>0?Z(t[0]):I()}))},className:(0,d.default)(b.element,b.button),color:"primary",variant:"contained"},y(l?"updateButton":"submitButton")),o.createElement(i.SeamlessButton,{onClick:O,className:(0,d.default)(b.element,b.button),color:"primary",variant:"outlined"},y("resetButton"))))}},36285:(e,t,n)=>{"use strict";n.d(t,{Z:()=>c});var r=n(55238),o=n(21324),a=n(5806),l=n(2411),s=n(6277);const c=function(e){var t=e.checked,n=e.name,c=e.color,i=e.handleChange,u=e.label,d=e.className,m=(0,r.Z)(e,["checked","name","color","handleChange","label","className"]);return l.createElement(a.Z,{className:(0,s.default)(d),control:l.createElement(o.Z,{checked:t,onChange:i,name:n,color:c}),label:u,disabled:m.disabled||!1})}},50921:(e,t,n)=>{"use strict";n.d(t,{Z:()=>s});var r=n(2411),o=n(70773),a=n(20973),l=n(29609);const s=function(e){var t=e.name,n=e.value,s=e.handleChange,c=e.disabled,i=void 0!==c&&c,u=(0,l.useTranslation)().t;return r.createElement(a.Z,null,(function(e){var a=e.passwordPolicies;return e.loading,r.createElement(o.SeamlessDropDown,{id:"password-policies-drop-down",required:!0,fullWidth:!0,disabled:i,label:u("passwordPolicy"),helperText:u("selectPasswordPolicy"),options:a.map((function(e){return{value:e.policyKey,key:e.name}})),name:t,value:n,onChange:s})}))}},20973:(e,t,n)=>{"use strict";n.d(t,{Z:()=>s});var r=n(74315),o=n(2411),a=n(96545),l=n(23664);const s=function(e){var t=e.children,n=o.useState([]),s=(0,r.Z)(n,2),c=s[0],i=s[1],u=o.useState(!1),d=(0,r.Z)(u,2),m=d[0],f=d[1],p=(0,l.Z)().showErrorMessage;return o.useEffect((function(){f(!0),(0,a.ob)().then((function(e){return i(e.passwordPolicies)})).catch((function(e){return p(e)})).finally((function(){return f(!1)}))}),[]),o.createElement(o.Fragment,null,t({passwordPolicies:c,loading:m}))}},23664:(e,t,n)=>{"use strict";n.d(t,{Z:()=>a});var r=n(19721),o=n(29609);const a=function(){var e=(0,r.useSnackbar)().enqueueSnackbar,t=(0,o.useTranslation)().t;function n(e){var n=t("anErrorOccurred");return["resultMessage","message","resultDescription"].forEach((function(t){e.hasOwnProperty(t)&&(n=e[t])})),n}return{showSuccessMessage:function(t){e("string"!=typeof t?n(t):t,{variant:"success"})},showErrorMessage:function(t){e("string"!=typeof t?n(t):t,{variant:"error"})},showInfoMessage:function(t){e("string"!=typeof t?n(t):t,{variant:"info"})},showWarningMessage:function(t){e("string"!=typeof t?n(t):t,{variant:"warning"})}}}}}]);