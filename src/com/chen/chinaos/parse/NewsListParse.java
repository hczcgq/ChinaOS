package com.chen.chinaos.parse;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.chen.chinaos.bean.News;

import android.util.Xml;

public class NewsListParse {
	private String ns;
	
	/**
	 * 开始解析
	 * @param in
	 * @return
	 */
	public List<News> ParseXML(InputStream in) {
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();
			return readOschina(parser);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 解析oschina结点
	 * @param parser
	 * @return
	 */
	private List<News> readOschina(XmlPullParser parser) {

		try {
			parser.require(XmlPullParser.START_TAG, ns, "oschina");
			while (parser.next() != XmlPullParser.END_TAG) {
				if (parser.getEventType() != XmlPullParser.START_TAG) {
					continue;
				}
				String name = parser.getName();
				if (name.equals("newslist")) {
					return readNewslist(parser);
				} else {
					skip(parser);
				}
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 解析newslist结点
	 * @param parser
	 * @return
	 */
	private List<News> readNewslist(XmlPullParser parser) {
		List<News> newsList = new ArrayList<News>();
		try {
			parser.require(XmlPullParser.START_TAG, ns, "newslist");
			while (parser.next() != XmlPullParser.END_TAG) {
				if (parser.getEventType() != XmlPullParser.START_TAG) {
					continue;
				}
				String name = parser.getName();
				if (name.equals("news")) {
					newsList.add(readNews(parser));
				} else {
					skip(parser);
				}
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newsList;

	}

	/**
	 * 解析news
	 * @param parser
	 * @return
	 */
	private News readNews(XmlPullParser parser) {
		String id = null;
		String title = null;
		String commentCount = null;
		String author = null;
		String pubDate = null;
		News news= new News();
		try {
			parser.require(XmlPullParser.START_TAG, ns, "news");

			while (parser.next() != XmlPullParser.END_TAG) {
				if (parser.getEventType() != XmlPullParser.START_TAG) {
					continue;
				}
				String name = parser.getName();
				if (name.equals("id")) {
					id = readId(parser);
					news.setTitle(id);
				} else if (name.equals("title")) {
					title = readTitle(parser);
					news.setTitle(title);
				} else if (name.equals("commentCount")) {
					commentCount = readCommentCount(parser);
					news.setCommentCount(commentCount);
				} else if (name.equals("author")) {
					author = readAuthor(parser);
					news.setAuthor(author);
				} else if (name.equals("pubDate")) {
					pubDate = readPubDate(parser);
					news.setPubDate(pubDate);
				} else {
					skip(parser);
				}

			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return news;
	}

	private String readId(XmlPullParser parser) {
		try {
			parser.require(XmlPullParser.START_TAG, ns, "id");
			String id = readText(parser);
			parser.require(XmlPullParser.END_TAG, ns, "id");
			return id;
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String readPubDate(XmlPullParser parser) {
		try {
			parser.require(XmlPullParser.START_TAG, ns, "pubDate");
			String pubDate = readText(parser);
			parser.require(XmlPullParser.END_TAG, ns, "pubDate");
			return pubDate;
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	private String readAuthor(XmlPullParser parser) {
		try {
			parser.require(XmlPullParser.START_TAG, ns, "author");
			String author = readText(parser);
			parser.require(XmlPullParser.END_TAG, ns, "author");
			return author;
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	private String readCommentCount(XmlPullParser parser) {
		try {
			parser.require(XmlPullParser.START_TAG, ns, "commentCount");
			String commentCount = readText(parser);
			parser.require(XmlPullParser.END_TAG, ns, "commentCount");
			return commentCount;
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	private String readTitle(XmlPullParser parser) {
		try {
			parser.require(XmlPullParser.START_TAG, ns, "title");
			String title = readText(parser);
			parser.require(XmlPullParser.END_TAG, ns, "title");
			return title;
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	private String readText(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		String result = "";
		if (parser.next() == XmlPullParser.TEXT) {
			result = parser.getText();
			parser.nextTag();
		}
		return result;
	}

	private void skip(XmlPullParser parser) {
		try {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				throw new IllegalStateException();
			}
			int depth = 1;
			while (depth != 0) {
				switch (parser.next()) {
				case XmlPullParser.END_TAG:
					depth--;
					break;
				case XmlPullParser.START_TAG:
					depth++;
					break;
				}
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
