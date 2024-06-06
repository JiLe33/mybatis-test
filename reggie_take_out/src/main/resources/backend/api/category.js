// 查询列表接口
const getCategoryPage = (params) => {
  return $axios({
      url: '/category/page',
      method: 'get',
      params
  });
}

// 编辑页面反查详情接口
const queryCategoryById = (id) => {
  return $axios({
    url: `/category/${id}`,
    method: 'get'
  })
}

// 删除当前列的接口
const deleCategory = (id) => {
  return $axios({
    url: '/category',
    method: 'delete',
    params: { id }
  })
}

// 修改接口
const editCategory = (params) => {
  return $axios({
    url: '/category',
    method: 'put',
    data: { ...params }
  })
}

// 新增接口
const addCategory = (params) => {
  return $axios({
    url: '/category',
    method: 'post',
    data: { ...params }
  })
}
// const success = false;
// async function $axios(params) {
//     return new Promise((resolve, reject) => {
//         $.ajax(params.url,{
//             success(r){
//                 resolve(r)
//             },
//             fail(r) {
//                 reject(r)
//             },
//         })
        // const response = {
        //     data: true,
        //     msg: 'ok',
        //     code: 1,
        // }
        // const fail = {
        //     data: false,
        //     msg: 'fail',
        //     code: 2,
        // };
        // if (success) {
        //     resolve(response);
        // } else {
        //     reject(fail);
        // }


