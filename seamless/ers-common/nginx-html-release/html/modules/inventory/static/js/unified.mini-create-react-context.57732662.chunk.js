(self.webpackChunk_unified_inventory=self.webpackChunk_unified_inventory||[]).push([[3206],{85053:(t,e,n)=>{"use strict";n.d(e,{Z:()=>l});var o=n(12801),r=n.n(o);function i(t,e){return(i=Object.setPrototypeOf||function(t,e){return t.__proto__=e,t})(t,e)}function u(t,e){t.prototype=Object.create(e.prototype),t.prototype.constructor=t,i(t,e)}var s=n(13980),c=n.n(s),a=1073741823,p="undefined"!=typeof globalThis?globalThis:"undefined"!=typeof window?window:void 0!==n.g?n.g:{};function f(t){var e=[];return{on:function(t){e.push(t)},off:function(t){e=e.filter((function(e){return e!==t}))},get:function(){return t},set:function(n,o){t=n,e.forEach((function(e){return e(t,o)}))}}}const l=r().createContext||function(t,e){var n,r,i="__create-react-context-"+(p["__global_unique_id__"]=(p.__global_unique_id__||0)+1)+"__",s=function(t){function n(){var e;return(e=t.apply(this,arguments)||this).emitter=f(e.props.value),e}u(n,t);var o=n.prototype;return o.getChildContext=function(){var t;return(t={})[i]=this.emitter,t},o.componentWillReceiveProps=function(t){if(this.props.value!==t.value){var n,o=this.props.value,r=t.value;((i=o)===(u=r)?0!==i||1/i==1/u:i!=i&&u!=u)?n=0:(n="function"==typeof e?e(o,r):a,0!=(n|=0)&&this.emitter.set(t.value,n))}var i,u},o.render=function(){return this.props.children},n}(o.Component);s.childContextTypes=((n={})[i]=c().object.isRequired,n);var l=function(e){function n(){var t;return(t=e.apply(this,arguments)||this).state={value:t.getValue()},t.onUpdate=function(e,n){0!=((0|t.observedBits)&n)&&t.setState({value:t.getValue()})},t}u(n,e);var o=n.prototype;return o.componentWillReceiveProps=function(t){var e=t.observedBits;this.observedBits=null==e?a:e},o.componentDidMount=function(){this.context[i]&&this.context[i].on(this.onUpdate);var t=this.props.observedBits;this.observedBits=null==t?a:t},o.componentWillUnmount=function(){this.context[i]&&this.context[i].off(this.onUpdate)},o.getValue=function(){return this.context[i]?this.context[i].get():t},o.render=function(){return(t=this.props.children,Array.isArray(t)?t[0]:t)(this.state.value);var t},n}(o.Component);return l.contextTypes=((r={})[i]=c().object,r),{Provider:s,Consumer:l}}}}]);