package hu.clickhouse.spyder.session;

import hu.clickhouse.spyder.entities.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("adWordList")
public class AdWordList extends EntityQuery<AdWord> {

	private static final String EJBQL = "select adWord from AdWord adWord";

	private static final String[] RESTRICTIONS = {
			"lower(adWord.adWord) like lower(concat(#{adWordList.adWord.adWord},'%'))",
			"lower(adWord.gDomain) like lower(concat(#{adWordList.adWord.gDomain},'%'))", };

	private AdWord adWord = new AdWord();

	public AdWordList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public AdWord getAdWord() {
		return adWord;
	}
}
