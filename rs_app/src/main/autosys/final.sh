#!/usr/bin/env bash
PROP_FILES=properties/*
TEMPLATE_FILES=templates/*

for p in $PROP_FILES
do
	for t in $TEMPLATE_FILES
	do
		PROP_FILE=$p
		PROPS=`cat $PROP_FILE`
		ENV=""
		CMD="sed "
		while IFS= read -r line; 
		do
			IFS='=' read -ra prop <<< "$line"
			CMD+="-e s/\\$\{${prop[0]}}/${prop[1]}/ "
			if [[ "${prop[0]}" == "ENV" ]]; then
				ENV=${prop[1]}
			fi

		done < $PROP_FILE

		FILENAME=$t
		FNAME=${FILENAME##*/}

		mkdir -p app/jils/${ENV,,}/

		touch app/jils/${ENV,,}/$FNAME$ENV.jil

		CMD+=" $2 >> app/jils/${ENV,,}/$FNAME$ENV.jil"

		rm -f app/jils/${ENV,,}/$FNAME$ENV.jil

		#output redirected to /dev/null
		#because some trash is printed 
		#when sed can't find proper placeholder
		`$CMD &>/dev/null`

	done

done

