for x in ../test/easyaccept/us-{users,leagues,win-loss,standings,join}.txt; do
	java -cp ../../bin:../../jars/easyaccept.jar:../../jars/jeplite-0.8.7a-bin.jar easyaccept.EasyAccept blms.facade.BlmsFacade $x
done
