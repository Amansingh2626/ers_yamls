(self.webpackChunk_unified_reseller=self.webpackChunk_unified_reseller||[]).push([["src_components_resellerTypeList_ResellerTypeListGetter_tsx"],{61357:(e,t,n)=>{"use strict";function r(e,t){(null==t||t>e.length)&&(t=e.length);for(var n=0,r=new Array(t);n<t;n++)r[n]=e[n];return r}n.d(t,{Z:()=>r})},74315:(e,t,n)=>{"use strict";n.d(t,{Z:()=>o});var r=n(70237);function o(e,t){return function(e){if(Array.isArray(e))return e}(e)||function(e,t){var n=e&&("undefined"!=typeof Symbol&&e[Symbol.iterator]||e["@@iterator"]);if(null!=n){var r,o,c=[],s=!0,u=!1;try{for(n=n.call(e);!(s=(r=n.next()).done)&&(c.push(r.value),!t||c.length!==t);s=!0);}catch(e){u=!0,o=e}finally{try{s||null==n.return||n.return()}finally{if(u)throw o}}return c}}(e,t)||(0,r.Z)(e,t)||function(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}()}},70237:(e,t,n)=>{"use strict";n.d(t,{Z:()=>o});var r=n(61357);function o(e,t){if(e){if("string"==typeof e)return(0,r.Z)(e,t);var n=Object.prototype.toString.call(e).slice(8,-1);return"Object"===n&&e.constructor&&(n=e.constructor.name),"Map"===n||"Set"===n?Array.from(e):"Arguments"===n||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n)?(0,r.Z)(e,t):void 0}}},84496:(e,t,n)=>{"use strict";n.d(t,{Z:()=>o});var r=n(61416);const o=n.n(r)()},58752:(e,t,n)=>{"use strict";n.d(t,{K:()=>d});var r,o,c,s=n(84496),u=window.config,a=null!==(r=null==u?void 0:u.showActiveContracts)&&void 0!==r&&r,i=null!==(o=null==u||null===(c=u.resellerAdditionalConfig)||void 0===c?void 0:c.activeContractLimit)&&void 0!==o?o:0,d=function(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:"",t=(e+=a?";contractStatus=1":"").includes("?")?"&":"?",n=i>0&&!e.includes("size=")?"".concat(t,"size=").concat(i):"";return e+=n,(0,s.Z)("/acms/v2/contracts".concat(e))}},24157:(e,t,n)=>{"use strict";n.d(t,{T:()=>o});var r=n(84496),o=function(e){return(0,r.Z)("/groupmanagementsystem/v1/group/member/findGroups?userId=".concat(e))}},96545:(e,t,n)=>{"use strict";n.d(t,{fZ:()=>r.fZ,sC:()=>r.sC,CQ:()=>r.CQ,hm:()=>r.hm,c1:()=>r.c1,pN:()=>r.pN,wv:()=>r.wv,OT:()=>r.OT,it:()=>r.it,eW:()=>r.eW,Fg:()=>r.Fg,Nk:()=>r.Nk,qu:()=>r.qu,Nb:()=>r.Nb,kS:()=>r.kS,GX:()=>r.GX,iA:()=>r.iA,OV:()=>r.OV,gH:()=>r.gH,E5:()=>r.E5,wm:()=>r.wm,TF:()=>r.TF,Ah:()=>r.Ah,HB:()=>r.HB,XD:()=>r.XD,FF:()=>r.FF,lX:()=>r.lX,Gh:()=>r.Gh,NF:()=>r.NF,cG:()=>o.cG,ZV:()=>o.ZV,cY:()=>o.cY,F3:()=>o.F3,b7:()=>o.b7,QX:()=>c.QX,lg:()=>c.lg,uP:()=>c.uP,G0:()=>c.G0,Iq:()=>c.Iq,ec:()=>s.ec,p9:()=>s.p9,ob:()=>s.ob,Kf:()=>u.K,t9:()=>a.t9,Sb:()=>a.Sb,wy:()=>a.wy,sQ:()=>a.ZV,g4:()=>a.g4,j8:()=>a.j8,WY:()=>a.WY,TJ:()=>i.T});var r=n(14674),o=n(31718),c=n(1111),s=n(95191),u=n(58752),a=n(35005),i=n(24157)},95191:(e,t,n)=>{"use strict";n.d(t,{ec:()=>o,ob:()=>c,Ww:()=>s,l4:()=>u,p9:()=>a});var r=n(84496),o=function(e){return(0,r.Z)("/dms/v1/password/policies",{method:"POST",body:e})},c=function(){return(0,r.Z)("/dms/v1/password/policies")},s=function(e){return(0,r.Z)("/dms/v1/password/policies/".concat(e))},u=function(e,t){return(0,r.Z)("/dms/v1/password/policies/".concat(e),{method:"PUT",body:t})},a=function(e){return(0,r.Z)("/dms/v1/password/policies/".concat(e),{method:"DELETE"})}},1111:(e,t,n)=>{"use strict";n.d(t,{fQ:()=>o,lg:()=>c,Iq:()=>s,uP:()=>u,G0:()=>a,Np:()=>i,QX:()=>d});var r=n(84496),o=function(e){return(0,r.Z)("/dms/v1/resellers/types/create",{method:"POST",body:e})},c=function(){return(0,r.Z)("/dms/v1/resellers/types/all")},s=function(e){return(0,r.Z)("/dms/v1/resellers/types/".concat(e,"/parents"))},u=function(e){return(0,r.Z)("/dms/v1/resellers/types/".concat(e,"/children"))},a=function(e){return(0,r.Z)("/dms/v1/resellers/types/".concat(e))},i=function(e){return(0,r.Z)("/dms/v1/resellers/types/update",{method:"PUT",body:e})},d=function(e){return(0,r.Z)("/dms/v1/resellers/types/".concat(e),{method:"DELETE"})}},14674:(e,t,n)=>{"use strict";n.d(t,{Nk:()=>i,Fg:()=>d,OV:()=>l,FF:()=>f,iA:()=>m,eW:()=>h,gH:()=>p,lX:()=>v,pN:()=>y,wv:()=>g,hm:()=>Z,kS:()=>w,uZ:()=>b,c0:()=>T,c1:()=>S,CQ:()=>P,E5:()=>E,NF:()=>O,qu:()=>I,fB:()=>F,OT:()=>R,HB:()=>G,XD:()=>N,wm:()=>U,it:()=>q,Gh:()=>X,sC:()=>D,fZ:()=>L,Ah:()=>B,v$:()=>M,GX:()=>_,Nb:()=>Q,TF:()=>V});var r,o,c=n(84496),s=window.config||{},u=(null==s?void 0:s.defaultPerPageItems)||20,a=null!==(r=null==s||null===(o=s.resellerAdditionalConfig)||void 0===o?void 0:o.callIsBasicResellerInfoAPI)&&void 0!==r&&r,i=function(){return(0,c.Z)("/groupmanagementsystem/v1/group?status=active")},d=function(e){return(0,c.Z)("/groupmanagementsystem/v1/group/name/".concat(e))},l=function(e){var t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:0,n=arguments.length>2?arguments[2]:void 0,r=n||u,o=t||n?"?limit=".concat(r,"&offset=").concat(t):"";return(0,c.Z)("/dms/v1/resellers/resellerChildren/".concat(e).concat(o))},f=function(e,t,n){var r=arguments.length>3&&void 0!==arguments[3]?arguments[3]:"",o=a?"searchResellerBasicInfoByAttribute":"searchResellersByAttribute";return r&&(o=r),(0,c.Z)("/dms/auth/".concat(o,"?limit=").concat(t,"&offSet=").concat(n),{method:"POST",body:e})},m=function(e){return(0,c.Z)("/dms/v1/resellers/types/".concat(e,"/parents"))},h=function(e,t,n){var r=n?"allowedTypes":t?"descendants":"children";return(0,c.Z)("/dms/v1/resellers/types/".concat(e,"/").concat(r))},p=function(e){return(0,c.Z)("/dms/auth/getResellerInfo",{method:"POST",body:e})},v=function(e){return(0,c.Z)("/dms/auth/updateReseller",{method:"POST",body:e})},y=function(e){return(0,c.Z)("/dms/auth/changeResellerType",{method:"POST",body:e})},g=function(e){return(0,c.Z)("/dms/auth/v1/resellerChangeState",{method:"POST",body:e})},Z=function(e,t,n){return(0,c.Z)("/dms/v1/lifecycle/dealer/status/".concat(n,"/").concat(t),{method:"POST",body:e})},w=function(){return(0,c.Z)("/lifecycle/reseller/status/fetch/labels")},b=function(e){return(0,c.Z)("/dms/auth/expirePassword",{method:"POST",body:e})},T=function(e){return(0,c.Z)("/dms/auth/forgetPassword",{method:"POST",body:e})},S=function(e){return(0,c.Z)("/dms/auth/changeParent?newResellerParentId=".concat(e.newResellerParentId,"&resellerId=").concat(e.resellerId),{method:"POST",body:e})},P=function(e){return(0,c.Z)("/dms/auth/generateId",{method:"POST",body:e})},E=function(e){var t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:"DMS";return(0,c.Z)("/template/v1/component/".concat(t,"/type/").concat(e))},O=function(e){return(0,c.Z)("/iprs/iprs-complete-check",{method:"POST",body:e})},I=function(e){return(0,c.Z)(e)},A=0,k=null,F=function(e){var t,n,r=new Headers;r.append("authorization",localStorage.getItem("token")||""),r.append("Accept-Language","en-US;en-IN;q=0.9,en-US;q=0.7,en-GB;q=0.6");var o={method:"POST",headers:r,body:e,redirect:"follow"},c="".concat(window.config.protocol,"://").concat(window.config.server,":").concat(window.config.port).concat("/api/dms/auth/addReseller"),s=null===(t=window)||void 0===t||null===(n=t.config)||void 0===n?void 0:n.isAllowedTokenRefresh;return fetch(c,o).then((function(t){var n=t.headers.get("authorization");return n?localStorage.setItem("token",n):s&&A<=0&&localStorage.getItem("token")&&(k=e,C().then((function(e){A=0,k&&F(k).then((function(e){0===e.resultCode&&window.history.back()}))})).catch((function(e){++A}))),t.json()}))},C=function(){var e="".concat(window.config.protocol,"://").concat(window.config.server,":").concat(window.config.port).concat(window.config.service,"/refreshToken");return(0,c.Z)(e,{method:"POST"})},R=function(e){return(0,c.Z)("/dms/v1/customers/?nationalId=".concat(e))},G=function(e){return(0,c.Z)("/lifecycle/reseller/".concat(e,"/transactions"))},N=function(e){return(0,c.Z)("/dms/v1/resellers/updatePayLimit",{method:"PUT",body:e})},U=function(e){return(0,c.Z)("/dms/auth/userAlreadyExists",{method:"POST",body:e})},q=function(e){return(0,c.Z)("/dms/auth/deleteResellerUsers",{method:"DELETE",body:e})},X=function(e){return(0,c.Z)("/dms/auth/updateResellerUsers",{method:"PUT",body:e})},D=function(e){return(0,c.Z)("/dms/auth/addResellerUsers",{method:"POST",body:e})},L=function(e,t){return(0,c.Z)("/groupmanagementsystem/v1/group/".concat(e,"/member"),{method:"POST",body:t})},B=function(e,t){return(0,c.Z)("/groupmanagementsystem/v1/group/".concat(e,"/member?memberIds=").concat(t),{method:"DELETE"})},M=function(e,t,n){return(0,c.Z)("/dms/auth/bulkSearchResellersByAttribute?limit=".concat(t,"&offSet=").concat(n),{method:"POST",body:e})},_=function(){return(0,c.Z)("/nms/api/messageTemplateType/CUSTOM/messages?page=0&size=".concat(u,"&sort=id%3Aasc"))},Q=function(){return(0,c.Z)("/scc/v1/frequencies")},V=function(e,t){return(0,c.Z)("/txe/reseller/".concat(e,"SubReseller"),{method:"POST",body:t})}},31718:(e,t,n)=>{"use strict";n.d(t,{cG:()=>o,F3:()=>c,cY:()=>s,b7:()=>u,ZV:()=>a});var r=n(84496),o=function(e){return(0,r.Z)("/dms/v1/roles/5",{method:"POST",body:e})},c=function(){return(0,r.Z)("/dms/v1/roles/")},s=function(e){return(0,r.Z)("/dms/v1/roles/".concat(5,"/").concat(e))},u=function(e,t){return(0,r.Z)("/dms/v1/roles/".concat(5,"/").concat(e),{method:"PUT",body:t})},a=function(e){return(0,r.Z)("/dms/v1/roles/".concat(5,"/").concat(e),{method:"DELETE"})}},35005:(e,t,n)=>{"use strict";n.d(t,{j8:()=>o,g4:()=>c,wy:()=>s,t9:()=>u,WY:()=>a,ZV:()=>i,Sb:()=>d});var r=n(84496),o=function(){return(0,r.Z)("/dms/v1/template/dropdown")},c=function(e){return(0,r.Z)("/dms/v1/template/dropdown/".concat(e))},s=function(){return(0,r.Z)("/dms/v1/template/dropdown/fields/all")},u=function(e){return(0,r.Z)("/dms/v1/template/dropdown",{method:"POST",body:e})},a=function(e,t){return(0,r.Z)("/dms/v1/template/dropdown/".concat(t),{method:"PUT",body:e})},i=function(e){return(0,r.Z)("/dms/v1/template/dropdown/byId/".concat(e),{method:"GET"})},d=function(e){return(0,r.Z)("/dms/v1/template/dropdown/".concat(e),{method:"DELETE"})}},68918:(e,t,n)=>{"use strict";n.r(t),n.d(t,{default:()=>a});var r=n(74315),o=n(2411),c=n(96545),s=n(23664),u=n(38670);const a=function(e){var t=e.children,n=(e.showError,e.type),a=o.useState([]),i=(0,r.Z)(a,2),d=i[0],l=i[1],f=o.useState(!1),m=(0,r.Z)(f,2),h=m[0],p=m[1],v=(0,s.Z)().showErrorMessage,y=(0,u.useSelector)((function(e){var t;return null==e||null===(t=e.auth)||void 0===t?void 0:t.user}),u.shallowEqual);return o.useEffect((function(){p(!0),"REGISTER_KYC"===n?(0,c.uP)(y.resellerType).then((function(e){return l(e.types)})).catch((function(e){return v(e)})).finally((function(){return p(!1)})):(0,c.lg)().then((function(e){return l(e.types)})).catch((function(e){return v(e)})).finally((function(){return p(!1)}))}),[]),o.createElement(o.Fragment,null,t({resellerTypes:d,loading:h}))}},23664:(e,t,n)=>{"use strict";n.d(t,{Z:()=>c});var r=n(19721),o=n(29609);const c=function(){var e=(0,r.useSnackbar)().enqueueSnackbar,t=(0,o.useTranslation)().t;function n(e){var n=t("anErrorOccurred");return["resultMessage","message","resultDescription"].forEach((function(t){e.hasOwnProperty(t)&&(n=e[t])})),n}return{showSuccessMessage:function(t){e("string"!=typeof t?n(t):t,{variant:"success"})},showErrorMessage:function(t){e("string"!=typeof t?n(t):t,{variant:"error"})},showInfoMessage:function(t){e("string"!=typeof t?n(t):t,{variant:"info"})},showWarningMessage:function(t){e("string"!=typeof t?n(t):t,{variant:"warning"})}}}}}]);