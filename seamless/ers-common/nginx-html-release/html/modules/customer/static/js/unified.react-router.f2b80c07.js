/*! For license information please see unified.react-router.f2b80c07.js.LICENSE.txt */
(self.webpackChunk_unified_config_grameenphone=self.webpackChunk_unified_config_grameenphone||[]).push([[486],{4149:(t,e,o)=>{"use strict";function r(t,e){return(r=Object.setPrototypeOf||function(t,e){return t.__proto__=e,t})(t,e)}o.d(e,{F0:()=>p,s6:()=>l,LX:()=>y});var n=o(8172),i=o.n(n),a=(o(3980),o(2585),o(5053));o(1898);var c=o(99),s=o.n(c);o(9744),o(3463);var u=function(t){var e=(0,a.Z)();return e.displayName="Router-History",e}(),l=function(t){var e=(0,a.Z)();return e.displayName="Router",e}(),p=function(t){function e(e){var o;return(o=t.call(this,e)||this).state={location:e.history.location},o._isMounted=!1,o._pendingLocation=null,e.staticContext||(o.unlisten=e.history.listen((function(t){o._isMounted?o.setState({location:t}):o._pendingLocation=t}))),o}var o,n;n=t,(o=e).prototype=Object.create(n.prototype),o.prototype.constructor=o,r(o,n),e.computeRootMatch=function(t){return{path:"/",url:"/",params:{},isExact:"/"===t}};var a=e.prototype;return a.componentDidMount=function(){this._isMounted=!0,this._pendingLocation&&this.setState({location:this._pendingLocation})},a.componentWillUnmount=function(){this.unlisten&&this.unlisten()},a.render=function(){return i().createElement(l.Provider,{value:{history:this.props.history,location:this.state.location,match:e.computeRootMatch(this.state.location.pathname),staticContext:this.props.staticContext}},i().createElement(u.Provider,{children:this.props.children||null,value:this.props.history}))},e}(i().Component);i().Component,i().Component;var f={},m=0;function y(t,e){void 0===e&&(e={}),("string"==typeof e||Array.isArray(e))&&(e={path:e});var o=e,r=o.path,n=o.exact,i=void 0!==n&&n,a=o.strict,c=void 0!==a&&a,u=o.sensitive,l=void 0!==u&&u;return[].concat(r).reduce((function(e,o){if(!o&&""!==o)return null;if(e)return e;var r=function(t,e){var o=""+e.end+e.strict+e.sensitive,r=f[o]||(f[o]={});if(r[t])return r[t];var n=[],i={regexp:s()(t,n,e),keys:n};return m<1e4&&(r[t]=i,m++),i}(o,{end:i,strict:c,sensitive:l}),n=r.regexp,a=r.keys,u=n.exec(t);if(!u)return null;var p=u[0],y=u.slice(1),h=t===p;return i&&!h?null:{path:o,url:"/"===o&&""===p?"/":p,isExact:h,params:a.reduce((function(t,e,o){return t[e.name]=y[o],t}),{})}}),null)}i().Component,i().Component,i().Component,i().useContext},1897:(t,e)=>{"use strict";var o="function"==typeof Symbol&&Symbol.for;o&&Symbol.for("react.element"),o&&Symbol.for("react.portal"),o&&Symbol.for("react.fragment"),o&&Symbol.for("react.strict_mode"),o&&Symbol.for("react.profiler"),o&&Symbol.for("react.provider"),o&&Symbol.for("react.context"),o&&Symbol.for("react.async_mode"),o&&Symbol.for("react.concurrent_mode"),o&&Symbol.for("react.forward_ref"),o&&Symbol.for("react.suspense"),o&&Symbol.for("react.suspense_list"),o&&Symbol.for("react.memo"),o&&Symbol.for("react.lazy"),o&&Symbol.for("react.block"),o&&Symbol.for("react.fundamental"),o&&Symbol.for("react.responder"),o&&Symbol.for("react.scope")},9744:(t,e,o)=>{"use strict";o(1897)}}]);