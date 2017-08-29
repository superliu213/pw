var mockFlag = false;

var interUrl = {
  basic: "/api/",
  common: {
    login: "login",
    menu: "menu",
    report: "smartbiSessionMonitorServlet"
  },
  user: {
    list: "user/list",
    add: "user/add",
    update: "user/update",
    remove: "user/remove",
    init: "user/init",
    role: "user/role",
    userrole: "user/userrole",
    group: "user/group",
    usergroup: "user/usergroup",
    passwordreset : "user/passwordreset",
    updatepassword : "user/updatepassword"
  },
  role: {
    list: "role/list",
    add: "role/add",
    update: "role/update",
    remove: "role/remove",
    init: "role/init",
    function: "role/function",
    rolefunction: "role/rolefunction"
  },
  group: {
    list: "group/list",
    add: "group/add",
    update: "group/update",
    remove: "group/remove",
    init: "group/init",
    role: "group/role",
    grouprole: "group/grouprole"
  },
  function: {
    list: "function/list",
    add: "function/add",
    update: "function/update",
    remove: "function/remove",
    init: "function/init",
    pagefunction: "function/pagefunction"
  },
  log: {
    list: "log/list",
    init: "log/init"
  }
};
