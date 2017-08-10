const Router = require('router');
const router = new Router();
//const fs = require('fs');

/* GET home page. */
router.get('/', function(req, res, next) {
    //fs.createReadStream("./views/index.html").pipe(res);
    res.send('Background server is up!')
});

module.exports = router;
