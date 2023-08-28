(self.webpackChunk_unified_inventory=self.webpackChunk_unified_inventory||[]).push([[7308],{77114:(e,r,t)=>{"use strict";t.d(r,{gN:()=>W,F2:()=>q,l0:()=>K,J9:()=>N,TA:()=>B});var n=t(840),a=t(78435),i=t.n(a),u=function(e){return function(e){return!!e&&"object"==typeof e}(e)&&!function(e){var r=Object.prototype.toString.call(e);return"[object RegExp]"===r||"[object Date]"===r||function(e){return e.$$typeof===o}(e)}(e)},o="function"==typeof Symbol&&Symbol.for?Symbol.for("react.element"):60103;function l(e,r){return!1!==r.clone&&r.isMergeableObject(e)?s((t=e,Array.isArray(t)?[]:{}),e,r):e;var t}function c(e,r,t){return e.concat(r).map((function(e){return l(e,t)}))}function s(e,r,t){(t=t||{}).arrayMerge=t.arrayMerge||c,t.isMergeableObject=t.isMergeableObject||u;var n=Array.isArray(r);return n===Array.isArray(e)?n?t.arrayMerge(e,r,t):function(e,r,t){var n={};return t.isMergeableObject(e)&&Object.keys(e).forEach((function(r){n[r]=l(e[r],t)})),Object.keys(r).forEach((function(a){t.isMergeableObject(r[a])&&e[a]?n[a]=s(e[a],r[a],t):n[a]=l(r[a],t)})),n}(e,r,t):l(r,t)}s.all=function(e,r){if(!Array.isArray(e))throw new Error("first argument should be an array");return e.reduce((function(e,t){return s(e,t,r)}),{})};const f=s;var d=t(81666),v=t(50985),p=t(2683),h=t(55518),y=t(73463),m=t.n(y),E=t(73089);function S(){return(S=Object.assign||function(e){for(var r=1;r<arguments.length;r++){var t=arguments[r];for(var n in t)Object.prototype.hasOwnProperty.call(t,n)&&(e[n]=t[n])}return e}).apply(this,arguments)}function b(e,r){if(null==e)return{};var t,n,a={},i=Object.keys(e);for(n=0;n<i.length;n++)t=i[n],r.indexOf(t)>=0||(a[t]=e[t]);return a}function T(e){if(void 0===e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return e}var g=function(e){return Array.isArray(e)&&0===e.length},A=function(e){return"function"==typeof e},_=function(e){return null!==e&&"object"==typeof e},O=function(e){return String(Math.floor(Number(e)))===e},R=function(e){return"[object String]"===Object.prototype.toString.call(e)},F=function(e){return 0===n.Children.count(e)},I=function(e){return _(e)&&A(e.then)};function C(e,r,t,n){void 0===n&&(n=0);for(var a=(0,p.Z)(r);e&&n<a.length;)e=e[a[n++]];return void 0===e?t:e}function k(e,r,t){for(var n=(0,v.Z)(e),a=n,i=0,u=(0,p.Z)(r);i<u.length-1;i++){var o=u[i],l=C(e,u.slice(0,i+1));if(l&&(_(l)||Array.isArray(l)))a=a[o]=(0,v.Z)(l);else{var c=u[i+1];a=a[o]=O(c)&&Number(c)>=0?[]:{}}}return(0===i?e:a)[u[i]]===t?e:(void 0===t?delete a[u[i]]:a[u[i]]=t,0===i&&void 0===t&&delete n[u[i]],n)}function M(e,r,t,n){void 0===t&&(t=new WeakMap),void 0===n&&(n={});for(var a=0,i=Object.keys(e);a<i.length;a++){var u=i[a],o=e[u];_(o)?t.get(o)||(t.set(o,!0),n[u]=Array.isArray(o)?[]:{},M(o,r,t,n[u])):n[u]=r}return n}var D=(0,n.createContext)(void 0),w=D.Provider,P=D.Consumer;function L(){var e=(0,n.useContext)(D);return e||(0,h.Z)(!1),e}function U(e,r){switch(r.type){case"SET_VALUES":return S({},e,{values:r.payload});case"SET_TOUCHED":return S({},e,{touched:r.payload});case"SET_ERRORS":return i()(e.errors,r.payload)?e:S({},e,{errors:r.payload});case"SET_STATUS":return S({},e,{status:r.payload});case"SET_ISSUBMITTING":return S({},e,{isSubmitting:r.payload});case"SET_ISVALIDATING":return S({},e,{isValidating:r.payload});case"SET_FIELD_VALUE":return S({},e,{values:k(e.values,r.payload.field,r.payload.value)});case"SET_FIELD_TOUCHED":return S({},e,{touched:k(e.touched,r.payload.field,r.payload.value)});case"SET_FIELD_ERROR":return S({},e,{errors:k(e.errors,r.payload.field,r.payload.value)});case"RESET_FORM":return S({},e,r.payload);case"SET_FORMIK_STATE":return r.payload(e);case"SUBMIT_ATTEMPT":return S({},e,{touched:M(e.values,!0),isSubmitting:!0,submitCount:e.submitCount+1});case"SUBMIT_FAILURE":case"SUBMIT_SUCCESS":return S({},e,{isSubmitting:!1});default:return e}}var V={},j={};function B(e){var r=e.validateOnChange,t=void 0===r||r,a=e.validateOnBlur,u=void 0===a||a,o=e.validateOnMount,l=void 0!==o&&o,c=e.isInitialValid,s=e.enableReinitialize,d=void 0!==s&&s,v=e.onSubmit,p=b(e,["validateOnChange","validateOnBlur","validateOnMount","isInitialValid","enableReinitialize","onSubmit"]),h=S({validateOnChange:t,validateOnBlur:u,validateOnMount:l,onSubmit:v},p),y=(0,n.useRef)(h.initialValues),m=(0,n.useRef)(h.initialErrors||V),E=(0,n.useRef)(h.initialTouched||j),T=(0,n.useRef)(h.initialStatus),g=(0,n.useRef)(!1),O=(0,n.useRef)({});(0,n.useEffect)((function(){return g.current=!0,function(){g.current=!1}}),[]);var F=(0,n.useReducer)(U,{values:h.initialValues,errors:h.initialErrors||V,touched:h.initialTouched||j,status:h.initialStatus,isSubmitting:!1,isValidating:!1,submitCount:0}),M=F[0],D=F[1],w=(0,n.useCallback)((function(e,r){return new Promise((function(t,n){var a=h.validate(e,r);null==a?t(V):I(a)?a.then((function(e){t(e||V)}),(function(e){n(e)})):t(a)}))}),[h.validate]),P=(0,n.useCallback)((function(e,r){var t=h.validationSchema,n=A(t)?t(r):t,a=r&&n.validateAt?n.validateAt(r,e):function(e,r,t,n){void 0===t&&(t=!1),void 0===n&&(n={});var a=x(e);return r[t?"validateSync":"validate"](a,{abortEarly:!1,context:n})}(e,n);return new Promise((function(e,r){a.then((function(){e(V)}),(function(t){"ValidationError"===t.name?e(function(e){var r={};if(e.inner){if(0===e.inner.length)return k(r,e.path,e.message);var t=e.inner,n=Array.isArray(t),a=0;for(t=n?t:t[Symbol.iterator]();;){var i;if(n){if(a>=t.length)break;i=t[a++]}else{if((a=t.next()).done)break;i=a.value}var u=i;C(r,u.path)||(r=k(r,u.path,u.message))}}return r}(t)):r(t)}))}))}),[h.validationSchema]),L=(0,n.useCallback)((function(e,r){return new Promise((function(t){return t(O.current[e].validate(r))}))}),[]),B=(0,n.useCallback)((function(e){var r=Object.keys(O.current).filter((function(e){return A(O.current[e].validate)})),t=r.length>0?r.map((function(r){return L(r,C(e,r))})):[Promise.resolve("DO_NOT_DELETE_YOU_WILL_BE_FIRED")];return Promise.all(t).then((function(e){return e.reduce((function(e,t,n){return"DO_NOT_DELETE_YOU_WILL_BE_FIRED"===t||t&&(e=k(e,r[n],t)),e}),{})}))}),[L]),N=(0,n.useCallback)((function(e){return Promise.all([B(e),h.validationSchema?P(e):{},h.validate?w(e):{}]).then((function(e){var r=e[0],t=e[1],n=e[2];return f.all([r,t,n],{arrayMerge:G})}))}),[h.validate,h.validationSchema,B,w,P]),H=Z((function(e){return void 0===e&&(e=M.values),D({type:"SET_ISVALIDATING",payload:!0}),N(e).then((function(e){return g.current&&(D({type:"SET_ISVALIDATING",payload:!1}),i()(M.errors,e)||D({type:"SET_ERRORS",payload:e})),e}))}));(0,n.useEffect)((function(){l&&!0===g.current&&i()(y.current,h.initialValues)&&H(y.current)}),[l,H]);var W=(0,n.useCallback)((function(e){var r=e&&e.values?e.values:y.current,t=e&&e.errors?e.errors:m.current?m.current:h.initialErrors||{},n=e&&e.touched?e.touched:E.current?E.current:h.initialTouched||{},a=e&&e.status?e.status:T.current?T.current:h.initialStatus;y.current=r,m.current=t,E.current=n,T.current=a;var i=function(){D({type:"RESET_FORM",payload:{isSubmitting:!!e&&!!e.isSubmitting,errors:t,touched:n,status:a,values:r,isValidating:!!e&&!!e.isValidating,submitCount:e&&e.submitCount&&"number"==typeof e.submitCount?e.submitCount:0}})};if(h.onReset){var u=h.onReset(M.values,se);I(u)?u.then(i):i()}else i()}),[h.initialErrors,h.initialStatus,h.initialTouched]);(0,n.useEffect)((function(){!0!==g.current||i()(y.current,h.initialValues)||(d&&(y.current=h.initialValues,W()),l&&H(y.current))}),[d,h.initialValues,W,l,H]),(0,n.useEffect)((function(){d&&!0===g.current&&!i()(m.current,h.initialErrors)&&(m.current=h.initialErrors||V,D({type:"SET_ERRORS",payload:h.initialErrors||V}))}),[d,h.initialErrors]),(0,n.useEffect)((function(){d&&!0===g.current&&!i()(E.current,h.initialTouched)&&(E.current=h.initialTouched||j,D({type:"SET_TOUCHED",payload:h.initialTouched||j}))}),[d,h.initialTouched]),(0,n.useEffect)((function(){d&&!0===g.current&&!i()(T.current,h.initialStatus)&&(T.current=h.initialStatus,D({type:"SET_STATUS",payload:h.initialStatus}))}),[d,h.initialStatus,h.initialTouched]);var K=Z((function(e){if(O.current[e]&&A(O.current[e].validate)){var r=C(M.values,e),t=O.current[e].validate(r);return I(t)?(D({type:"SET_ISVALIDATING",payload:!0}),t.then((function(e){return e})).then((function(r){D({type:"SET_FIELD_ERROR",payload:{field:e,value:r}}),D({type:"SET_ISVALIDATING",payload:!1})}))):(D({type:"SET_FIELD_ERROR",payload:{field:e,value:t}}),Promise.resolve(t))}return h.validationSchema?(D({type:"SET_ISVALIDATING",payload:!0}),P(M.values,e).then((function(e){return e})).then((function(r){D({type:"SET_FIELD_ERROR",payload:{field:e,value:r[e]}}),D({type:"SET_ISVALIDATING",payload:!1})}))):Promise.resolve()})),z=(0,n.useCallback)((function(e,r){var t=r.validate;O.current[e]={validate:t}}),[]),Y=(0,n.useCallback)((function(e){delete O.current[e]}),[]),$=Z((function(e,r){return D({type:"SET_TOUCHED",payload:e}),(void 0===r?u:r)?H(M.values):Promise.resolve()})),J=(0,n.useCallback)((function(e){D({type:"SET_ERRORS",payload:e})}),[]),q=Z((function(e,r){var n=A(e)?e(M.values):e;return D({type:"SET_VALUES",payload:n}),(void 0===r?t:r)?H(n):Promise.resolve()})),Q=(0,n.useCallback)((function(e,r){D({type:"SET_FIELD_ERROR",payload:{field:e,value:r}})}),[]),X=Z((function(e,r,n){return D({type:"SET_FIELD_VALUE",payload:{field:e,value:r}}),(void 0===n?t:n)?H(k(M.values,e,r)):Promise.resolve()})),ee=(0,n.useCallback)((function(e,r){var t,n=r,a=e;if(!R(e)){e.persist&&e.persist();var i=e.target?e.target:e.currentTarget,u=i.type,o=i.name,l=i.id,c=i.value,s=i.checked,f=(i.outerHTML,i.options),d=i.multiple;n=r||o||l,a=/number|range/.test(u)?(t=parseFloat(c),isNaN(t)?"":t):/checkbox/.test(u)?function(e,r,t){if("boolean"==typeof e)return Boolean(r);var n=[],a=!1,i=-1;if(Array.isArray(e))n=e,a=(i=e.indexOf(t))>=0;else if(!t||"true"==t||"false"==t)return Boolean(r);return r&&t&&!a?n.concat(t):a?n.slice(0,i).concat(n.slice(i+1)):n}(C(M.values,n),s,c):d?function(e){return Array.from(e).filter((function(e){return e.selected})).map((function(e){return e.value}))}(f):c}n&&X(n,a)}),[X,M.values]),re=Z((function(e){if(R(e))return function(r){return ee(r,e)};ee(e)})),te=Z((function(e,r,t){return void 0===r&&(r=!0),D({type:"SET_FIELD_TOUCHED",payload:{field:e,value:r}}),(void 0===t?u:t)?H(M.values):Promise.resolve()})),ne=(0,n.useCallback)((function(e,r){e.persist&&e.persist();var t=e.target,n=t.name,a=t.id,i=(t.outerHTML,r||n||a);te(i,!0)}),[te]),ae=Z((function(e){if(R(e))return function(r){return ne(r,e)};ne(e)})),ie=(0,n.useCallback)((function(e){A(e)?D({type:"SET_FORMIK_STATE",payload:e}):D({type:"SET_FORMIK_STATE",payload:function(){return e}})}),[]),ue=(0,n.useCallback)((function(e){D({type:"SET_STATUS",payload:e})}),[]),oe=(0,n.useCallback)((function(e){D({type:"SET_ISSUBMITTING",payload:e})}),[]),le=Z((function(){return D({type:"SUBMIT_ATTEMPT"}),H().then((function(e){var r=e instanceof Error;if(!r&&0===Object.keys(e).length){var t;try{if(void 0===(t=fe()))return}catch(e){throw e}return Promise.resolve(t).then((function(e){return g.current&&D({type:"SUBMIT_SUCCESS"}),e})).catch((function(e){if(g.current)throw D({type:"SUBMIT_FAILURE"}),e}))}if(g.current&&(D({type:"SUBMIT_FAILURE"}),r))throw e}))})),ce=Z((function(e){e&&e.preventDefault&&A(e.preventDefault)&&e.preventDefault(),e&&e.stopPropagation&&A(e.stopPropagation)&&e.stopPropagation(),le().catch((function(e){console.warn("Warning: An unhandled error was caught from submitForm()",e)}))})),se={resetForm:W,validateForm:H,validateField:K,setErrors:J,setFieldError:Q,setFieldTouched:te,setFieldValue:X,setStatus:ue,setSubmitting:oe,setTouched:$,setValues:q,setFormikState:ie,submitForm:le},fe=Z((function(){return v(M.values,se)})),de=Z((function(e){e&&e.preventDefault&&A(e.preventDefault)&&e.preventDefault(),e&&e.stopPropagation&&A(e.stopPropagation)&&e.stopPropagation(),W()})),ve=(0,n.useCallback)((function(e){return{value:C(M.values,e),error:C(M.errors,e),touched:!!C(M.touched,e),initialValue:C(y.current,e),initialTouched:!!C(E.current,e),initialError:C(m.current,e)}}),[M.errors,M.touched,M.values]),pe=(0,n.useCallback)((function(e){return{setValue:function(r,t){return X(e,r,t)},setTouched:function(r,t){return te(e,r,t)},setError:function(r){return Q(e,r)}}}),[X,te,Q]),he=(0,n.useCallback)((function(e){var r=_(e),t=r?e.name:e,n=C(M.values,t),a={name:t,value:n,onChange:re,onBlur:ae};if(r){var i=e.type,u=e.value,o=e.as,l=e.multiple;"checkbox"===i?void 0===u?a.checked=!!n:(a.checked=!(!Array.isArray(n)||!~n.indexOf(u)),a.value=u):"radio"===i?(a.checked=n===u,a.value=u):"select"===o&&l&&(a.value=a.value||[],a.multiple=!0)}return a}),[ae,re,M.values]),ye=(0,n.useMemo)((function(){return!i()(y.current,M.values)}),[y.current,M.values]),me=(0,n.useMemo)((function(){return void 0!==c?ye?M.errors&&0===Object.keys(M.errors).length:!1!==c&&A(c)?c(h):c:M.errors&&0===Object.keys(M.errors).length}),[c,ye,M.errors,h]);return S({},M,{initialValues:y.current,initialErrors:m.current,initialTouched:E.current,initialStatus:T.current,handleBlur:ae,handleChange:re,handleReset:de,handleSubmit:ce,resetForm:W,setErrors:J,setFormikState:ie,setFieldTouched:te,setFieldValue:X,setFieldError:Q,setStatus:ue,setSubmitting:oe,setTouched:$,setValues:q,submitForm:le,validateForm:H,validateField:K,isValid:me,dirty:ye,unregisterField:Y,registerField:z,getFieldProps:he,getFieldMeta:ve,getFieldHelpers:pe,validateOnBlur:u,validateOnChange:t,validateOnMount:l})}function N(e){var r=B(e),t=e.component,a=e.children,i=e.render,u=e.innerRef;return(0,n.useImperativeHandle)(u,(function(){return r})),(0,n.createElement)(w,{value:r},t?(0,n.createElement)(t,r):i?i(r):a?A(a)?a(r):F(a)?null:n.Children.only(a):null)}function x(e){var r=Array.isArray(e)?[]:{};for(var t in e)if(Object.prototype.hasOwnProperty.call(e,t)){var n=String(t);!0===Array.isArray(e[n])?r[n]=e[n].map((function(e){return!0===Array.isArray(e)||(0,d.Z)(e)?x(e):""!==e?e:void 0})):(0,d.Z)(e[n])?r[n]=x(e[n]):r[n]=""!==e[n]?e[n]:void 0}return r}function G(e,r,t){var n=e.slice();return r.forEach((function(r,a){if(void 0===n[a]){var i=!1!==t.clone&&t.isMergeableObject(r);n[a]=i?f(Array.isArray(r)?[]:{},r,t):r}else t.isMergeableObject(r)?n[a]=f(e[a],r,t):-1===e.indexOf(r)&&n.push(r)})),n}var H="undefined"!=typeof window&&void 0!==window.document&&void 0!==window.document.createElement?n.useLayoutEffect:n.useEffect;function Z(e){var r=(0,n.useRef)(e);return H((function(){r.current=e})),(0,n.useCallback)((function(){for(var e=arguments.length,t=new Array(e),n=0;n<e;n++)t[n]=arguments[n];return r.current.apply(void 0,t)}),[])}function W(e){var r=e.validate,t=e.name,a=e.render,i=e.children,u=e.as,o=e.component,l=b(e,["validate","name","render","children","as","component"]),c=b(L(),["validate","validationSchema"]),s=c.registerField,f=c.unregisterField;(0,n.useEffect)((function(){return s(t,{validate:r}),function(){f(t)}}),[s,f,t,r]);var d=c.getFieldProps(S({name:t},l)),v=c.getFieldMeta(t),p={field:d,form:c};if(a)return a(S({},p,{meta:v}));if(A(i))return i(S({},p,{meta:v}));if(o){if("string"==typeof o){var h=l.innerRef,y=b(l,["innerRef"]);return(0,n.createElement)(o,S({ref:h},d,y),i)}return(0,n.createElement)(o,S({field:d,form:c},l),i)}var m=u||"input";if("string"==typeof m){var E=l.innerRef,T=b(l,["innerRef"]);return(0,n.createElement)(m,S({ref:E},d,T),i)}return(0,n.createElement)(m,S({},d,l),i)}var K=(0,n.forwardRef)((function(e,r){var t=e.action,a=b(e,["action"]),i=t||"#",u=L(),o=u.handleReset,l=u.handleSubmit;return(0,n.createElement)("form",Object.assign({onSubmit:l,ref:r,onReset:o,action:i},a))}));function z(e){var r=function(r){return(0,n.createElement)(P,null,(function(t){return t||(0,h.Z)(!1),(0,n.createElement)(e,Object.assign({},r,{formik:t}))}))},t=e.displayName||e.name||e.constructor&&e.constructor.name||"Component";return r.WrappedComponent=e,r.displayName="FormikConnect("+t+")",m()(r,e)}K.displayName="Form";var Y=function(e,r,t){var n=$(e);return n.splice(r,0,t),n},$=function(e){if(e){if(Array.isArray(e))return[].concat(e);var r=Object.keys(e).map((function(e){return parseInt(e)})).reduce((function(e,r){return r>e?r:e}),0);return Array.from(S({},e,{length:r+1}))}return[]},J=function(e){function r(r){var t;return(t=e.call(this,r)||this).updateArrayField=function(e,r,n){var a=t.props,i=a.name;(0,a.formik.setFormikState)((function(t){var a="function"==typeof n?n:e,u="function"==typeof r?r:e,o=k(t.values,i,e(C(t.values,i))),l=n?a(C(t.errors,i)):void 0,c=r?u(C(t.touched,i)):void 0;return g(l)&&(l=void 0),g(c)&&(c=void 0),S({},t,{values:o,errors:n?k(t.errors,i,l):t.errors,touched:r?k(t.touched,i,c):t.touched})}))},t.push=function(e){return t.updateArrayField((function(r){return[].concat($(r),[(0,E.Z)(e)])}),!1,!1)},t.handlePush=function(e){return function(){return t.push(e)}},t.swap=function(e,r){return t.updateArrayField((function(t){return function(e,r,t){var n=$(e),a=n[r];return n[r]=n[t],n[t]=a,n}(t,e,r)}),!0,!0)},t.handleSwap=function(e,r){return function(){return t.swap(e,r)}},t.move=function(e,r){return t.updateArrayField((function(t){return function(e,r,t){var n=$(e),a=n[r];return n.splice(r,1),n.splice(t,0,a),n}(t,e,r)}),!0,!0)},t.handleMove=function(e,r){return function(){return t.move(e,r)}},t.insert=function(e,r){return t.updateArrayField((function(t){return Y(t,e,r)}),(function(r){return Y(r,e,null)}),(function(r){return Y(r,e,null)}))},t.handleInsert=function(e,r){return function(){return t.insert(e,r)}},t.replace=function(e,r){return t.updateArrayField((function(t){return function(e,r,t){var n=$(e);return n[r]=t,n}(t,e,r)}),!1,!1)},t.handleReplace=function(e,r){return function(){return t.replace(e,r)}},t.unshift=function(e){var r=-1;return t.updateArrayField((function(t){var n=t?[e].concat(t):[e];return r<0&&(r=n.length),n}),(function(e){var t=e?[null].concat(e):[null];return r<0&&(r=t.length),t}),(function(e){var t=e?[null].concat(e):[null];return r<0&&(r=t.length),t})),r},t.handleUnshift=function(e){return function(){return t.unshift(e)}},t.handleRemove=function(e){return function(){return t.remove(e)}},t.handlePop=function(){return function(){return t.pop()}},t.remove=t.remove.bind(T(t)),t.pop=t.pop.bind(T(t)),t}var t,a;a=e,(t=r).prototype=Object.create(a.prototype),t.prototype.constructor=t,t.__proto__=a;var u=r.prototype;return u.componentDidUpdate=function(e){this.props.validateOnChange&&this.props.formik.validateOnChange&&!i()(C(e.formik.values,e.name),C(this.props.formik.values,this.props.name))&&this.props.formik.validateForm(this.props.formik.values)},u.remove=function(e){var r;return this.updateArrayField((function(t){var n=t?$(t):[];return r||(r=n[e]),A(n.splice)&&n.splice(e,1),n}),!0,!0),r},u.pop=function(){var e;return this.updateArrayField((function(r){var t=r;return e||(e=t&&t.pop&&t.pop()),t}),!0,!0),e},u.render=function(){var e={push:this.push,pop:this.pop,swap:this.swap,move:this.move,insert:this.insert,replace:this.replace,unshift:this.unshift,remove:this.remove,handlePush:this.handlePush,handlePop:this.handlePop,handleSwap:this.handleSwap,handleMove:this.handleMove,handleInsert:this.handleInsert,handleReplace:this.handleReplace,handleUnshift:this.handleUnshift,handleRemove:this.handleRemove},r=this.props,t=r.component,a=r.render,i=r.children,u=r.name,o=S({},e,{form:b(r.formik,["validate","validationSchema"]),name:u});return t?(0,n.createElement)(t,o):a?a(o):i?"function"==typeof i?i(o):F(i)?null:n.Children.only(i):null},r}(n.Component);J.defaultProps={validateOnChange:!0};var q=z(J);n.Component,n.Component}}]);