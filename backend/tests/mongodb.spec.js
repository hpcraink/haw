describe("MongoDB", function() {
  it("is there a server running", function(next) {
    const MongoClient = require('mongodb').MongoClient;
    MongoClient.connect('mongodb://127.0.0.1:27017', function(err, db) {
      expect(err).toBe(null);
      next();
    })
  })
})
