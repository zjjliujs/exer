app.service("monitorService",
["$http","$resource"
, function($http, $resource){
        var self = this;

        /*
        self.getClusters = function() {
            var clustersRes = $resource("data/clusters.json",{});
            return clustersRes.query();
        };

        self.getNodeList = function(name) {
            var nodesRes = $resource("data/:name.json",{name:name});
            return nodesRes.query();
        };
        */

        self.showDialog = function(v){
            if (arguments.length > 0) {
                self.pageStat.showDialog= v;
            }
            return self.pageStat.showDialog;
        };

        self.dialogUrl = function (url) {
            if (arguments.length > 0){
                self.pageStat.dialog.url = url;
            }
            return self.pageStat.dialog.url;
        };

        self.hideDialog = function () {
            self.showDialog(false);
            self.dialogUrl(null);
        };

        self.getClusterTypes = function (){
            return [
                {name:'web集群'},
                {name:'Cassandra集群'},
                {name:'ES集群'}
            ];
        };

        self.pageStat = {
            showDialog:false,
            dialog:{
                url:"dialogs/cluster-creator.html"
            },
            clusterCreator:{
                clusterName:null,
                clusterType:null
            },
            nodeCreator:{
                cluster:null,
                hostName:null,
                ip:null
            },
            nodeList:{
                cluster:null,
                order:{
                    type:null,
                    asc:false
                }
            }
        };
    }])
