(self.webpackChunk_unified_reseller=self.webpackChunk_unified_reseller||[]).push([["src_components_ResellerChildrenDropDown_index_tsx","src_components_ResellerChildrenList_ResellerChildrenListGetter_tsx"],{7560:(e,t,n)=>{"use strict";function r(){return(r=Object.assign||function(e){for(var t=1;t<arguments.length;t++){var n=arguments[t];for(var r in n)Object.prototype.hasOwnProperty.call(n,r)&&(e[r]=n[r])}return e}).apply(this,arguments)}n.d(t,{Z:()=>r})},96545:(e,t,n)=>{"use strict";n.d(t,{fZ:()=>r.fZ,sC:()=>r.sC,CQ:()=>r.CQ,hm:()=>r.hm,c1:()=>r.c1,pN:()=>r.pN,wv:()=>r.wv,OT:()=>r.OT,it:()=>r.it,eW:()=>r.eW,Fg:()=>r.Fg,Nk:()=>r.Nk,qu:()=>r.qu,Nb:()=>r.Nb,kS:()=>r.kS,GX:()=>r.GX,iA:()=>r.iA,OV:()=>r.OV,gH:()=>r.gH,E5:()=>r.E5,wm:()=>r.wm,TF:()=>r.TF,Ah:()=>r.Ah,HB:()=>r.HB,XD:()=>r.XD,FF:()=>r.FF,lX:()=>r.lX,Gh:()=>r.Gh,NF:()=>r.NF,cG:()=>o.cG,ZV:()=>o.ZV,cY:()=>o.cY,F3:()=>o.F3,b7:()=>o.b7,QX:()=>c.QX,lg:()=>c.lg,uP:()=>c.uP,G0:()=>c.G0,Iq:()=>c.Iq,ec:()=>s.ec,p9:()=>s.p9,ob:()=>s.ob,Kf:()=>a.K,t9:()=>l.t9,Sb:()=>l.Sb,wy:()=>l.wy,sQ:()=>l.ZV,g4:()=>l.g4,j8:()=>l.j8,WY:()=>l.WY,TJ:()=>u.T});var r=n(14674),o=n(31718),c=n(1111),s=n(95191),a=n(58752),l=n(35005),u=n(24157)},95191:(e,t,n)=>{"use strict";n.d(t,{ec:()=>o,ob:()=>c,Ww:()=>s,l4:()=>a,p9:()=>l});var r=n(84496),o=function(e){return(0,r.Z)("/dms/v1/password/policies",{method:"POST",body:e})},c=function(){return(0,r.Z)("/dms/v1/password/policies")},s=function(e){return(0,r.Z)("/dms/v1/password/policies/".concat(e))},a=function(e,t){return(0,r.Z)("/dms/v1/password/policies/".concat(e),{method:"PUT",body:t})},l=function(e){return(0,r.Z)("/dms/v1/password/policies/".concat(e),{method:"DELETE"})}},1111:(e,t,n)=>{"use strict";n.d(t,{fQ:()=>o,lg:()=>c,Iq:()=>s,uP:()=>a,G0:()=>l,Np:()=>u,QX:()=>i});var r=n(84496),o=function(e){return(0,r.Z)("/dms/v1/resellers/types/create",{method:"POST",body:e})},c=function(){return(0,r.Z)("/dms/v1/resellers/types/all")},s=function(e){return(0,r.Z)("/dms/v1/resellers/types/".concat(e,"/parents"))},a=function(e){return(0,r.Z)("/dms/v1/resellers/types/".concat(e,"/children"))},l=function(e){return(0,r.Z)("/dms/v1/resellers/types/".concat(e))},u=function(e){return(0,r.Z)("/dms/v1/resellers/types/update",{method:"PUT",body:e})},i=function(e){return(0,r.Z)("/dms/v1/resellers/types/".concat(e),{method:"DELETE"})}},14674:(e,t,n)=>{"use strict";n.d(t,{Nk:()=>u,Fg:()=>i,OV:()=>d,FF:()=>m,iA:()=>f,eW:()=>h,gH:()=>p,lX:()=>v,pN:()=>g,wv:()=>Z,hm:()=>y,kS:()=>b,uZ:()=>w,c0:()=>S,c1:()=>T,CQ:()=>P,E5:()=>E,NF:()=>I,qu:()=>O,fB:()=>R,OT:()=>F,HB:()=>A,XD:()=>N,wm:()=>G,it:()=>x,Gh:()=>_,sC:()=>q,fZ:()=>X,Ah:()=>B,v$:()=>U,GX:()=>L,Nb:()=>W,TF:()=>M});var r,o,c=n(84496),s=window.config||{},a=(null==s?void 0:s.defaultPerPageItems)||20,l=null!==(r=null==s||null===(o=s.resellerAdditionalConfig)||void 0===o?void 0:o.callIsBasicResellerInfoAPI)&&void 0!==r&&r,u=function(){return(0,c.Z)("/groupmanagementsystem/v1/group?status=active")},i=function(e){return(0,c.Z)("/groupmanagementsystem/v1/group/name/".concat(e))},d=function(e){var t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:0,n=arguments.length>2?arguments[2]:void 0,r=n||a,o=t||n?"?limit=".concat(r,"&offset=").concat(t):"";return(0,c.Z)("/dms/v1/resellers/resellerChildren/".concat(e).concat(o))},m=function(e,t,n){var r=arguments.length>3&&void 0!==arguments[3]?arguments[3]:"",o=l?"searchResellerBasicInfoByAttribute":"searchResellersByAttribute";return r&&(o=r),(0,c.Z)("/dms/auth/".concat(o,"?limit=").concat(t,"&offSet=").concat(n),{method:"POST",body:e})},f=function(e){return(0,c.Z)("/dms/v1/resellers/types/".concat(e,"/parents"))},h=function(e,t,n){var r=n?"allowedTypes":t?"descendants":"children";return(0,c.Z)("/dms/v1/resellers/types/".concat(e,"/").concat(r))},p=function(e){return(0,c.Z)("/dms/auth/getResellerInfo",{method:"POST",body:e})},v=function(e){return(0,c.Z)("/dms/auth/updateReseller",{method:"POST",body:e})},g=function(e){return(0,c.Z)("/dms/auth/changeResellerType",{method:"POST",body:e})},Z=function(e){return(0,c.Z)("/dms/auth/v1/resellerChangeState",{method:"POST",body:e})},y=function(e,t,n){return(0,c.Z)("/dms/v1/lifecycle/dealer/status/".concat(n,"/").concat(t),{method:"POST",body:e})},b=function(){return(0,c.Z)("/lifecycle/reseller/status/fetch/labels")},w=function(e){return(0,c.Z)("/dms/auth/expirePassword",{method:"POST",body:e})},S=function(e){return(0,c.Z)("/dms/auth/forgetPassword",{method:"POST",body:e})},T=function(e){return(0,c.Z)("/dms/auth/changeParent?newResellerParentId=".concat(e.newResellerParentId,"&resellerId=").concat(e.resellerId),{method:"POST",body:e})},P=function(e){return(0,c.Z)("/dms/auth/generateId",{method:"POST",body:e})},E=function(e){var t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:"DMS";return(0,c.Z)("/template/v1/component/".concat(t,"/type/").concat(e))},I=function(e){return(0,c.Z)("/iprs/iprs-complete-check",{method:"POST",body:e})},O=function(e){return(0,c.Z)(e)},C=0,k=null,R=function(e){var t,n,r=new Headers;r.append("authorization",localStorage.getItem("token")||""),r.append("Accept-Language","en-US;en-IN;q=0.9,en-US;q=0.7,en-GB;q=0.6");var o={method:"POST",headers:r,body:e,redirect:"follow"},c="".concat(window.config.protocol,"://").concat(window.config.server,":").concat(window.config.port).concat("/api/dms/auth/addReseller"),s=null===(t=window)||void 0===t||null===(n=t.config)||void 0===n?void 0:n.isAllowedTokenRefresh;return fetch(c,o).then((function(t){var n=t.headers.get("authorization");return n?localStorage.setItem("token",n):s&&C<=0&&localStorage.getItem("token")&&(k=e,D().then((function(e){C=0,k&&R(k).then((function(e){0===e.resultCode&&window.history.back()}))})).catch((function(e){++C}))),t.json()}))},D=function(){var e="".concat(window.config.protocol,"://").concat(window.config.server,":").concat(window.config.port).concat(window.config.service,"/refreshToken");return(0,c.Z)(e,{method:"POST"})},F=function(e){return(0,c.Z)("/dms/v1/customers/?nationalId=".concat(e))},A=function(e){return(0,c.Z)("/lifecycle/reseller/".concat(e,"/transactions"))},N=function(e){return(0,c.Z)("/dms/v1/resellers/updatePayLimit",{method:"PUT",body:e})},G=function(e){return(0,c.Z)("/dms/auth/userAlreadyExists",{method:"POST",body:e})},x=function(e){return(0,c.Z)("/dms/auth/deleteResellerUsers",{method:"DELETE",body:e})},_=function(e){return(0,c.Z)("/dms/auth/updateResellerUsers",{method:"PUT",body:e})},q=function(e){return(0,c.Z)("/dms/auth/addResellerUsers",{method:"POST",body:e})},X=function(e,t){return(0,c.Z)("/groupmanagementsystem/v1/group/".concat(e,"/member"),{method:"POST",body:t})},B=function(e,t){return(0,c.Z)("/groupmanagementsystem/v1/group/".concat(e,"/member?memberIds=").concat(t),{method:"DELETE"})},U=function(e,t,n){return(0,c.Z)("/dms/auth/bulkSearchResellersByAttribute?limit=".concat(t,"&offSet=").concat(n),{method:"POST",body:e})},L=function(){return(0,c.Z)("/nms/api/messageTemplateType/CUSTOM/messages?page=0&size=".concat(a,"&sort=id%3Aasc"))},W=function(){return(0,c.Z)("/scc/v1/frequencies")},M=function(e,t){return(0,c.Z)("/txe/reseller/".concat(e,"SubReseller"),{method:"POST",body:t})}},35005:(e,t,n)=>{"use strict";n.d(t,{j8:()=>o,g4:()=>c,wy:()=>s,t9:()=>a,WY:()=>l,ZV:()=>u,Sb:()=>i});var r=n(84496),o=function(){return(0,r.Z)("/dms/v1/template/dropdown")},c=function(e){return(0,r.Z)("/dms/v1/template/dropdown/".concat(e))},s=function(){return(0,r.Z)("/dms/v1/template/dropdown/fields/all")},a=function(e){return(0,r.Z)("/dms/v1/template/dropdown",{method:"POST",body:e})},l=function(e,t){return(0,r.Z)("/dms/v1/template/dropdown/".concat(t),{method:"PUT",body:e})},u=function(e){return(0,r.Z)("/dms/v1/template/dropdown/byId/".concat(e),{method:"GET"})},i=function(e){return(0,r.Z)("/dms/v1/template/dropdown/".concat(e),{method:"DELETE"})}},84647:(e,t,n)=>{"use strict";n.d(t,{Z:()=>d});var r=n(7560),o=n(11746),c=n(74315),s=n(2411),a=n(7089),l=n(86619),u=n(77954),i=n(67358);const d=function(e){var t=e.label,n=e.placeholder,d=void 0===n?"Please choose":n,m=e.data,f=e.handleChange,h=e.elemId,p=e.selectedItems,v=void 0===p?[]:p,g=e.disabledItems,Z=void 0===g?[]:g,y=e.loading,b=void 0!==y&&y,w=e.style,S=void 0===w?{}:w,T=e.disabled,P=void 0!==T&&T,E=e.color,I=void 0===E?"primary":E,O=s.useState(v),C=(0,c.Z)(O,2),k=C[0],R=C[1];h=h||"auto-complete- ".concat(Math.random().toString(36).substring(7));var D=k.map((function(e){return e.resellerId})),F=v.map((function(e){return e.resellerId})),A=Z.map((function(e){return e.resellerId})),N=[].concat((0,o.Z)(F),(0,o.Z)(A)).filter((function(e){return D.includes(e)}));return(0,s.useEffect)((function(){v.length>0&&R(v)}),[v]),m=m.filter((function(e){return!N.includes(e.resellerId)})),s.createElement("div",null,b?s.createElement(i.Z,null):null,s.createElement(u.ZP,{multiple:!0,fullWidth:!0,id:h,disabled:P,value:k,onChange:function(e,t){var n=[].concat((0,o.Z)(Z),(0,o.Z)(t.filter((function(e){return!A.includes(e.resellerId)}))));f(n),R(n)},options:m,getOptionLabel:function(e){return e.resellerName},renderTags:function(e,t){return e.map((function(e,n){return s.createElement(a.Z,(0,r.Z)({label:e.resellerName},t({index:n}),{disabled:A.includes(e.resellerId),color:I}))}))},style:S,renderInput:function(e){return s.createElement(l.Z,(0,r.Z)({},e,{label:t,variant:"outlined",placeholder:d}))}}))}},58152:(e,t,n)=>{"use strict";n.r(t),n.d(t,{default:()=>d});var r=n(2411),o=n(38670),c=n(33553),s=n(24276),a=n(60905),l=n(70773),u=n(40431),i=n(84647);const d=function(e){var t=e.label,n=e.name,d=e.selectedResellersIds,m=e.handleChange,f=e.addHandler,h=(0,o.useSelector)((function(e){return e.auth})).user.requestedPrincipalId;return r.createElement(c.Z,{display:"flex",flexDirection:"row",mt:3},r.createElement("div",{style:{minWidth:"80%"}},r.createElement(u.default,{id:h},(function(e){var o=e.resellersData,c=e.loading,u=[];return d&&d.length>0&&(u=o.filter((function(e){return null==d?void 0:d.includes(e.resellerId)}))),r.createElement(a.Z,{container:!0,item:!0,xs:12},r.createElement(a.Z,{item:!0,xs:10},r.createElement(i.Z,{data:o,loading:c,selectedItems:u,label:t,color:"selectedAdminsIds"===n?"primary":"secondary",handleChange:function(e){var t=e.map((function(e){return e.resellerId}));m({target:{value:t,name:n}})}})),r.createElement(a.Z,{item:!0,xs:2},r.createElement(l.SeamlessButton,{onClick:function(e){return f(u)},color:"primary",variant:"text"},r.createElement(s.Z,{fontSize:"large"},"group_add"))))}))))}},40431:(e,t,n)=>{"use strict";n.r(t),n.d(t,{default:()=>u});var r=n(11746),o=n(74315),c=n(2411),s=n(96545),a=n(23664),l=n(91955);const u=function(e){var t=e.children,n=e.id,u=e.searchData,i=e.searchedData,d=e.offset,m=void 0===d?0:d,f=e.limit,h=e.perPage,p=c.useState([]),v=(0,o.Z)(p,2),g=v[0],Z=v[1],y=c.useState([]),b=(0,o.Z)(y,2),w=b[0],S=b[1],T=c.useState(!1),P=(0,o.Z)(T,2),E=P[0],I=P[1],O=c.useState(0),C=(0,o.Z)(O,2),k=C[0],R=C[1],D=(0,a.Z)().showErrorMessage,F=[],A=[],N=c.useCallback((function(e,t){I(!0),(0,s.OV)(n,t,e).then((function(e){A=(0,l.dh)(e.resellers);var t=[].concat((0,r.Z)(F),(0,r.Z)(A));F=t,Z(A),S(t),R(e.totalCount)})).catch((function(e){return D(e)})).finally((function(){return I(!1)}))}),[]);return c.useEffect((function(){var e;i&&(null===(e=Object.values(i))||void 0===e?void 0:e.length)>0||u&&(null==u?void 0:u.length)>0?Z(null!=u?u:[]):(f=h,(0===m||m<k)&&N(f,m))}),[f,m,h,u]),c.createElement(c.Fragment,null,t({resellersData:g,lazzyResellersData:w,totalCount:k,loading:E}))}},23664:(e,t,n)=>{"use strict";n.d(t,{Z:()=>c});var r=n(19721),o=n(29609);const c=function(){var e=(0,r.useSnackbar)().enqueueSnackbar,t=(0,o.useTranslation)().t;function n(e){var n=t("anErrorOccurred");return["resultMessage","message","resultDescription"].forEach((function(t){e.hasOwnProperty(t)&&(n=e[t])})),n}return{showSuccessMessage:function(t){e("string"!=typeof t?n(t):t,{variant:"success"})},showErrorMessage:function(t){e("string"!=typeof t?n(t):t,{variant:"error"})},showInfoMessage:function(t){e("string"!=typeof t?n(t):t,{variant:"info"})},showWarningMessage:function(t){e("string"!=typeof t?n(t):t,{variant:"warning"})}}}}}]);