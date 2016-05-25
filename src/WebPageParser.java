/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testapp;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import Common.UrlLink;


/**
 *
 * @author atrushin
 */
public class WebPageParser {

    public WebPageParser() {
    }  
    
    private static WebPageParser Instance;
    
    public static WebPageParser getInstance() {
      if(Instance == null) {
         Instance = new WebPageParser();
      }
      return Instance;
   }
    
    
    
    
    
    private WebClient InitWebClient() {
        WebClient client;
        client = new WebClient(BrowserVersion.CHROME);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setThrowExceptionOnFailingStatusCode(false);
        client.getOptions().setThrowExceptionOnScriptError(false);
        
        return client;
    }
    
    
    
    public List<UrlLink> GetAllLinksSkySports(int resultsCount) {
        ArrayList<UrlLink> result = new ArrayList<>();
        String url = "http://www.skysports.com/football/news";
        try{
             
            WebClient client = InitWebClient();
             
             HtmlPage page = client.getPage(url);
             String pageContent = page.asXml();
             
             HtmlAnchor anchor = null;
             while(result.size() < resultsCount) {
                result.addAll(  ParseWebContent(pageContent)  );                  
                page.getAnchorByText("Show More");
                anchor = page.getAnchorByText("Show More");
                page = anchor.click();
                pageContent = page.asXml();
                
                result = removeDuplicates(result);
             }
                      
        }
        catch(Exception ex) {
        }
        
        return result;
    }
    
    private ArrayList<UrlLink> removeDuplicates(ArrayList<UrlLink> list) {
        // Store unique items in result.
	ArrayList<UrlLink> result = new ArrayList<>();

	// Record encountered Strings in HashSet.
	HashSet<String> set = new HashSet<>();

	// Loop over argument list.
	for (UrlLink item : list) {

	    // If String is not in set, add it to the list and the set.
	    if (!set.contains(item.URL)) {
		result.add(item);
		set.add(item.URL);
	    }
            else {
                System.out.println("== Dublicate detected ==");
                System.out.println("URL: " + item.URL + "   Title: " + item.Title);
                System.out.println("==========================");
            }
	}
	return result;
    }
    
    private List<UrlLink> ParseWebContent(String pageContent) {
        
        Document doc = Jsoup.parse(pageContent);
        Elements links = doc.select(".news-list__item.news-list__item--show-thumb-bp30");
        
        ArrayList<UrlLink> urlLinks = new ArrayList<>();
        Element anchor = null;  
        
        //System.out.println("== Matrix H calculation ==");
        for(Element link : links) {
            UrlLink toList = new UrlLink();
            anchor = link.select(".news-list__headline-link").first();
            toList.URL = anchor.attr("href");
            toList.Title = anchor.text();
            toList.PageContent = getPageContent(toList.URL);
            //System.out.println(toList);
            urlLinks.add(toList);
        }
        //System.out.println("==========================");
        
        return urlLinks;
    }
    
    
    private String getPageContent(String Url){
        String result = "";        
        try{
            WebClient client = InitWebClient();
            HtmlPage page = client.getPage(Url);
            String pageContent = page.asXml(); 
            
            Document doc = Jsoup.parse(pageContent);
            Element pageBody = doc.select(".article__body").first(); //"article__body article__body--lead"
            result = pageBody.text();
        }
        catch(Exception ex) {
            
        }                
        return result;
    }
    
}
