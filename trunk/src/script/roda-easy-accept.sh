JARS=../../jars/easyaccept.jar:../../jars/jeplite-0.8.7a-bin.jar:../../jars/bloat-1.0.jar:../../jars/db4o-6.1-db4ounit.jar:../../jars/db4o-6.1-java5.jar:../../jars/db4o-6.1-nqopt.jar

#for x in ../test/easyaccept/us-{users,leagues,win-loss,standings,join}.txt; do
#	java -cp ../../bin:$JARS easyaccept.EasyAccept blms.facade.BlmsFacade $x
#done
	java -cp ../../bin:$JARS easyaccept.EasyAccept blms.facade.BlmsFacade ../test/easyaccept/us-{users,leagues,win-loss,standings,join}.txt
