var mockFlag = true;

var interUrl = {
    basic: "/api/",
    common : {
        login : "login",
        menu : "menu"
    },
    user: {
        list: "user/list",
        add: "user/add",
        update: "user/update",
        remove: "user/remove"
    },
    role: {
        list: "role/list",
        add: "role/add",
        update: "role/update",
        remove: "role/remove",
        group: "role/group",
        save : "role/save"
    },
    group: {
        list: "group/list",
        add: "group/add",
        update: "group/update",
        remove: "group/remove",
        role: "group/role",
        save: "group/save"
    },
    function: {
        list: "function/list",
        add: "function/add",
        update: "function/update",
        remove: "function/remove"
    },
    curd : {
        list : "curd/list"
    },
    paging : {
        list : "paging/list"
    },
    form : {
        query : "form/query"
    }
};

// isNo = function (value, row, index) {
//     return [null, "是", "否"][value] || null;
// };

window.mockFlag = mockFlag;
window.interUrl = interUrl;
